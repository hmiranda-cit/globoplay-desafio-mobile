/* Copyright © 2024 CI&T. All Rights Reserved.
 * Unauthorized copying of this file's contents, via any medium, is strictly prohibited
 * Proprietary
 * Written by Henrique Miranda <hmiranda@ciandt.com>
 */

package com.ciandt.globo.tmdb.app.home

import generated.openapi.client.DefaultApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

private const val BEST_POSTER_SIZE = "original"
private const val POSTER_SIZE_REGEX = """w[0-9]+"""

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
}
