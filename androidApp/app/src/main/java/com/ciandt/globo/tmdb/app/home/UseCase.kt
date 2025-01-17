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

data class TvSeriesContent(
    val series: List<SectionContent>,
    val soapOperas: List<SectionContent>,
)

class HomeUseCase(
    private val apiClient: DefaultApi,
    private val ioDispatcher: CoroutineDispatcher,
    private val workDispatcher: CoroutineDispatcher,
) {
    suspend fun loadPosterPathUrlPrefix(): String {
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

    suspend fun loadMovieSectionContent(posterPathUrlPrefix: String): List<SectionContent> {
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

    suspend fun loadTvSeriesContent(posterPathUrlPrefix: String): TvSeriesContent {
        return withContext(ioDispatcher) {
            val soapGenreIdDeferred = async { loadSoapOperaGenreId() }
            val response = apiClient.discoverTv()
            withContext(workDispatcher) {
                val results = response.results
                require(!results.isNullOrEmpty())

                val series = mutableListOf<SectionContent>()
                val soapOperas = mutableListOf<SectionContent>()
                val tvSeriesContent = TvSeriesContent(series, soapOperas)

                val soapOperaGenreId = soapGenreIdDeferred.await()
                for (result in results) {
                    val accessibilityDescription = result.name.orEmpty()
                    val imageModel = "$posterPathUrlPrefix${result.posterPath}" ?: continue
                    val content = SectionContent(accessibilityDescription, imageModel)

                    val destination = if (result.genreIds?.contains(soapOperaGenreId) == true) soapOperas else series
                    destination.add(content)
                }

                tvSeriesContent
            }
        }
    }

    private suspend fun loadSoapOperaGenreId(): Int? {
        val response = withContext(ioDispatcher) { apiClient.genreTvList() }
        return withContext(workDispatcher) {
            val soapOperaGenre = requireNotNull(response.genres).first { it.name == SOAP_OPERA_GENRE_NAME }
            soapOperaGenre.id
        }
    }
}
