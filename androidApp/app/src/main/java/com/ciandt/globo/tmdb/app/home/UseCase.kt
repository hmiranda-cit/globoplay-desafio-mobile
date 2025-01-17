/* Copyright © 2024 CI&T. All Rights Reserved.
 * Unauthorized copying of this file's contents, via any medium, is strictly prohibited
 * Proprietary
 * Written by Henrique Miranda <hmiranda@ciandt.com>
 */

package com.ciandt.globo.tmdb.app.home

import generated.openapi.client.DefaultApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

private const val BEST_POSTER_SIZE = "original"
private const val POSTER_SIZE_REGEX = """w[0-9]+"""
private const val SOAP_OPERA_GENRE_NAME = "Soap"

class HomeUseCase(
    private val apiClient: DefaultApi,
    private val ioDispatcher: CoroutineDispatcher,
    private val workDispatcher: CoroutineDispatcher,
) {
    suspend fun loadHomeScreenData(): HomeScreenData = withContext(workDispatcher) {
        val deferredSoapOperaGenreId = async { loadSoapOperaGenreId() }
        val posterPathUrlPrefix = loadPosterPathUrlPrefix()

        val movieData = async { loadMovieSectionContent(posterPathUrlPrefix) }

        val soapOperaGenreId = deferredSoapOperaGenreId.await()
        val deferredTvSeriesContent = async { loadTvSeriesContent(posterPathUrlPrefix, withoutGenres = soapOperaGenreId) }
        val soapOperasContent = loadTvSeriesContent(posterPathUrlPrefix, withGenres = soapOperaGenreId)

        HomeScreenData(movieData.await(), deferredTvSeriesContent.await(), soapOperasContent)
    }

    private suspend fun loadPosterPathUrlPrefix(): String {
        val response = withContext(ioDispatcher) { apiClient.configurationDetails() }
        return withContext(workDispatcher) {
            val configuration = requireNotNull(response.images)
            val baseUrl = requireNotNull(configuration.secureBaseUrl)
            val sizeSpecs = configuration.posterSizes
            require(!sizeSpecs.isNullOrEmpty())

            var bestAvailableSize = Int.MIN_VALUE
            var bestSizeSpec: String? = null
            val sizeSpecPattern = POSTER_SIZE_REGEX.toRegex()
            for (sizeSpec in sizeSpecs) {
                when {
                    sizeSpec == BEST_POSTER_SIZE -> {
                        bestSizeSpec = sizeSpec
                        break
                    }
                    sizeSpec.matches(sizeSpecPattern) -> {
                        val size = sizeSpec.drop(1).toInt()
                        if (bestAvailableSize < size) {
                            bestAvailableSize = size
                            bestSizeSpec = sizeSpec
                        }
                    }
                }
            }

            baseUrl + requireNotNull(bestSizeSpec)
        }
    }

    private suspend fun loadMovieSectionContent(posterPathUrlPrefix: String): List<SectionContent> {
        val response = withContext(ioDispatcher) { apiClient.discoverMovie() }
        return withContext(workDispatcher) {
            response.results
                ?.map {
                    val posterPath = requireNotNull(it.posterPath)
                    SectionContent(
                        requireNotNull(it.title),
                        "$posterPathUrlPrefix$posterPath",
                    )
                }
                .orEmpty()
        }
    }

    private suspend fun loadTvSeriesContent(
        posterPathUrlPrefix: String,
        withGenres: String? = null,
        withoutGenres: String? = null,
    ): List<SectionContent> {
        val response = withContext(ioDispatcher) { apiClient.discoverTv(withGenres = withGenres, withoutGenres = withoutGenres) }
        return withContext(workDispatcher) {
            response.results
                ?.map {
                    val posterPath = requireNotNull(it.posterPath)
                    SectionContent(requireNotNull(it.name), imageModel = "$posterPathUrlPrefix$posterPath")
                }
                .orEmpty()
        }
    }

    private suspend fun loadSoapOperaGenreId(): String? {
        val response = withContext(ioDispatcher) { apiClient.genreTvList() }
        return withContext(workDispatcher) {
            val soapOperaGenre = requireNotNull(response.genres).first { it.name == SOAP_OPERA_GENRE_NAME }
            soapOperaGenre.id?.toString()
        }
    }
}
