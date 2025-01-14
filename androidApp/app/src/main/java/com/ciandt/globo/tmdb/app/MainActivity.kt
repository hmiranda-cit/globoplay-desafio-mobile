/* Copyright © 2024 CI&T. All Rights Reserved.
 * Unauthorized copying of this file's contents, via any medium, is strictly prohibited
 * Proprietary
 * Written by Henrique Miranda <hmiranda@ciandt.com>
 */

package com.ciandt.globo.tmdb.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil3.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent { HomeScreen() }
    }
}

@Composable
@Preview
fun HomeScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterVertically),
        modifier = Modifier
            .background(color = Color(0xFF1F1F1F))
            .padding(horizontal = 32.dp)
            .fillMaxSize()
    ) {
        Section("Section 1",
            listOf(
                SectionContent("Mickey", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7oaspVjLS8F-NDM-C7m8YJHX1eSPlcvw197CMD7ryqhioXalaoOOb4-Z-lyQDqzUrouM&usqp=CAU"),
                SectionContent("Tintin", "https://bleedingcool.com/wp-content/uploads/2025/01/084fd4b8-5923-40d5-bfb5-7301d0b47221.jpeg"),
                SectionContent("Popeye", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQduJlbk6z6w-5aZqbWl21_PgRBMlH0_euTag&s"),
                SectionContent("Zé Carioca", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHHaJGZ3PcpUwQ2eXiI62mtjnHb-hfDafwhQ&s"),
            )
        )
        Section("Section 2", emptyList())
        Section("Section 3", emptyList())
    }
}

data class SectionContent(
    val accessibilityDescription: String,
    val imageModel: Any,
)

@Composable
fun Section(header: String, contents: List<SectionContent>, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
        modifier = modifier
    ) {
        BasicText(style = TextStyle(color = Color.White, fontSize = 32.sp), text = header)

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(contents) {
                AsyncImage(
                    contentDescription = it.accessibilityDescription,
                    contentScale = ContentScale.FillBounds,
                    model = it.imageModel,
                    placeholder = painterResource(R.drawable.placeholder_loading),
                    modifier = Modifier.requiredSize(width = 200.dp, height = 300.dp)
                )
            }
        }
    }
}
