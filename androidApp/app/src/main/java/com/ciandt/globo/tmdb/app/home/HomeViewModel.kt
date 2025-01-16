/* Copyright © 2024 CI&T. All Rights Reserved.
 * Unauthorized copying of this file's contents, via any medium, is strictly prohibited
 * Proprietary
 * Written by Henrique Miranda <hmiranda@ciandt.com>
 */

package com.ciandt.globo.tmdb.app.home

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val useCase: HomeUseCase,
    private val workerDispatcher: CoroutineDispatcher,
) {
    val movieSectionDataFlow = MutableSharedFlow<List<SectionContent>>(
        replay = 0,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.SUSPEND,
    )

    suspend fun onUiVisible() {
        withContext(workerDispatcher) {
            val posterPathUrlPrefix = useCase.loadPosterPathUrlPrefix()
            val data = useCase.loadMovieSectionContent(posterPathUrlPrefix)

            movieSectionDataFlow.emit(data)
        }
    }
}
