/* Copyright © 2024 CI&T. All Rights Reserved.
 * Unauthorized copying of this file's contents, via any medium, is strictly prohibited
 * Proprietary
 * Written by Henrique Miranda <hmiranda@ciandt.com>
 */

package com.ciandt.globo.tmdb.app

import android.app.Application
import android.net.TrafficStats
import android.os.StrictMode
import androidx.annotation.MainThread
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import generated.openapi.infrastructure.ApiClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val client = setupNetworkClient()
        setupStrictMode()

        setupCoilImageLoader(client)
    }

    private fun setupCoilImageLoader(client: OkHttpClient) {
        SingletonImageLoader.setSafe {
            ImageLoader.Builder(it)
                .components {
                    add(OkHttpNetworkFetcherFactory(client))
                }
                .build()
        }
    }

    private fun setupNetworkClient(): OkHttpClient {
        // Generate Secrets using gradle task: hideSecretFromPropertiesFile -PpropertiesFileName=apiKeys.properties
        val token = Secrets().getTmdbBearerToken(packageName)

        ApiClient.builder
            .addInterceptor {
                val request = it.request().newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                it.proceed(request)
            }
            .addInterceptor {
                val threadTag = Thread.currentThread().id.toInt()
                TrafficStats.setThreadStatsTag(threadTag)
                it.proceed(it.request())
            }
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )

        return ApiClient.defaultClient
    }

    @MainThread
    private fun setupStrictMode() {
        StrictMode.enableDefaults()

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyDialog()
                .penaltyLog()
                .build()
        )

        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyDeath()
                .penaltyLog()
                .build()
        )
    }
}
