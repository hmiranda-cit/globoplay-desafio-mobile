/* Copyright © 2024 CI&T. All Rights Reserved.
 * Unauthorized copying of this file's contents, via any medium, is strictly prohibited
 * Proprietary
 * Written by Henrique Miranda <hmiranda@ciandt.com>
 */

package com.ciandt.globo.tmdb.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    val referenceScreenHeightPx = 1280f
    val referenceBottomBarHeightPx = 112f

    Scaffold(
        bottomBar = {
            BottomAppBar(
                Modifier
                    .fillMaxHeight(referenceBottomBarHeightPx / referenceScreenHeightPx)
                    .fillMaxWidth()
            )
        },
        topBar = {},
        floatingActionButton = {},
    ) { innerPadding ->
        MovieList(Modifier.padding(innerPadding))
    }
}

@Composable
fun BottomAppBar(modifier: Modifier = Modifier) {
    BottomAppBar(
        backgroundColor = Color.Black,
        modifier = Modifier
            .then(modifier)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            BottomAppBarButton(
                iconRes = R.drawable.baseline_home_24,
                onClick = {},
                text = stringResource(R.string.home_screen_bottombar_button_text),
            )
        }
    }
}

@Composable
fun BottomAppBarButton(
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    Button(
        colors = buttonColors(backgroundColor = Color.Transparent),
        onClick = onClick,
        modifier = Modifier
            .then(modifier)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            modifier = Modifier
        ) {
            val sharedModifier = Modifier.align(Alignment.CenterHorizontally)
            Icon(
                tint = Color.White,
                contentDescription = null,
                painter = painterResource(iconRes),
                modifier = sharedModifier
            )
            BasicText(
                style = TextStyle(color = Color.White, fontSize = 16.sp),
                text = text,
                modifier = sharedModifier
            )
        }
    }
}

@Composable
@Preview
fun MovieList(modifier: Modifier = Modifier) {
    val lazyColumState = rememberLazyListState()
    LazyColumn(
        state = lazyColumState,
        verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterVertically),
        modifier = Modifier
            .background(color = Color(0xFF1F1F1F))
            .padding(horizontal = 32.dp)
            .fillMaxSize()
            .then(modifier)
    ) {
        item {
            Section(
                "Comics",
                listOf(
                    SectionContent("Mickey", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7oaspVjLS8F-NDM-C7m8YJHX1eSPlcvw197CMD7ryqhioXalaoOOb4-Z-lyQDqzUrouM&usqp=CAU"),
                    SectionContent("Tintin", "https://bleedingcool.com/wp-content/uploads/2025/01/084fd4b8-5923-40d5-bfb5-7301d0b47221.jpeg"),
                    SectionContent("Popeye", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQduJlbk6z6w-5aZqbWl21_PgRBMlH0_euTag&s"),
                    SectionContent("Zé Carioca", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHHaJGZ3PcpUwQ2eXiI62mtjnHb-hfDafwhQ&s"),
                )
            )
        }
        item {
            Section(
                "Movies",
                listOf(
                    SectionContent("Cinderela", "https://upload.wikimedia.org/wikipedia/pt/4/47/Cinderela_Baiana.jpg"),
                    SectionContent("Darko", "https://upload.wikimedia.org/wikipedia/pt/thumb/5/58/Donnie_Darko.jpg/220px-Donnie_Darko.jpg"),
                    SectionContent("Godfather", "https://upload.wikimedia.org/wikipedia/pt/a/af/The_Godfather%2C_The_Game.jpg"),
                    SectionContent("Juno", "https://upload.wikimedia.org/wikipedia/pt/thumb/6/6b/Juno_P%C3%B4ster.jpg/220px-Juno_P%C3%B4ster.jpg"),
                )
            )
        }
        item {
            Section(
                "Books",
                listOf(
                    SectionContent("Casmurro", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Dom_Casmurro.djvu/page1-369px-Dom_Casmurro.djvu.jpg"),
                    SectionContent("AM", "https://upload.wikimedia.org/wikipedia/en/thumb/4/47/IHaveNoMouth.jpg/220px-IHaveNoMouth.jpg"),
                    SectionContent("Orbis", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/Esmeraldo_de_situ_orbis%2C_1892.JPG/800px-Esmeraldo_de_situ_orbis%2C_1892.JPG"),
                    SectionContent("Juno", "https://upload.wikimedia.org/wikipedia/en/c/cb/The_Chronicles_of_Narnia_box_set_cover.jpg"),
                )
            )
        }
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

        val lazyRowState = rememberLazyListState()
        LazyRow(
            state = lazyRowState,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
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
