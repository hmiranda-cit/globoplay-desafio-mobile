/* Copyright © 2024 CI&T. All Rights Reserved.
 * Unauthorized copying of this file's contents, via any medium, is strictly prohibited
 * Proprietary
 * Written by Henrique Miranda <hmiranda@ciandt.com>
 */

package com.ciandt.globo.tmdb.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.text.BasicText
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent { BasicText("Hello World!") }
    }
}
