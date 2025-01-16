/* Copyright © 2024 CI&T. All Rights Reserved.
 * Unauthorized copying of this file's contents, via any medium, is strictly prohibited
 * Proprietary
 * Written by Henrique Miranda <hmiranda@ciandt.com>
 */

package com.ciandt.globo.tmdb.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ciandt.globo.tmdb.app.home.HomeScreen
import com.ciandt.globo.tmdb.app.home.HomeUseCase
import com.ciandt.globo.tmdb.app.home.HomeViewModel
import com.ciandt.globo.tmdb.app.home.SectionRenderingData
import generated.openapi.client.DefaultApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val sectionsRenderingData = setupObservers()
        setContent { HomeScreen(sectionsRenderingData) }
    }

    private fun setupObservers(): List<SectionRenderingData> {
        val sectionsRenderingData = mutableStateListOf<SectionRenderingData>()

        lifecycleScope.launch(Dispatchers.Main.immediate) {
            val movieSectionHeader = getString(R.string.home_screen_movie_section_header)
            val viewModel = HomeViewModel(
                HomeUseCase(DefaultApi(), Dispatchers.IO, Dispatchers.Default),
                Dispatchers.Default,
            )

            viewModel.movieSectionDataFlow
                .onEach { sectionsRenderingData.add(SectionRenderingData(it, movieSectionHeader)) }
                .launchIn(this)

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sectionsRenderingData.clear()
                viewModel.onUiVisible()
            }
        }

        return sectionsRenderingData
    }
}
