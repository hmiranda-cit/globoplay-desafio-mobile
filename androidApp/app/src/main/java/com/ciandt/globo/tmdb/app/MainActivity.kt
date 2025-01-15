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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil3.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent { HomeScreen(sectionRenderingDataPlaceholders) }
    }
}

@Composable
fun HomeScreen(sectionsRenderingData: List<SectionRenderingData>) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                Modifier
                    .requiredHeight(112.dp)
                    .fillMaxWidth()
            )
        },
        topBar = {
            TopBar(
                Modifier
                    .requiredHeight(192.dp)
                    .fillMaxWidth()
            )
        },
        floatingActionButton = {},
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        ContentList(
            sectionsRenderingData = sectionsRenderingData,
            modifier = Modifier
                .background(color = Color(0xFF1F1F1F))
                .fillMaxSize()
                .padding(innerPadding)
        )
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
fun TopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        backgroundColor = Color.Black,
        modifier = Modifier
            .then(modifier)
    ) {
        TopBarLogo(
            Modifier
                .requiredHeight(48.dp)
                .wrapContentWidth()
        )
    }
}

@Composable
fun TopBarLogo(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .then(modifier)
    ) {
        Image(
            colorFilter = ColorFilter.tint(Color.White),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            painter = painterResource(R.drawable.globoplay_logo),
            modifier = Modifier.fillMaxSize()
        )
    }
}

data class SectionRenderingData(
    val contents: List<SectionContent>,
    val header: String,
)

@Composable
fun ContentList(sectionsRenderingData: List<SectionRenderingData>, modifier: Modifier = Modifier) {
    Box(Modifier.then(modifier)) {
        val lazyColumState = rememberLazyListState()
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = lazyColumState,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        ) {
            items(sectionsRenderingData) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                ) {
                    ContentListSpacer()
                    Section(it.header, it.contents)
                    ContentListSpacer()
                }
            }
        }
    }
}

@Composable
fun ContentListSpacer(modifier: Modifier = Modifier) {
    Spacer(
        Modifier
            .requiredHeight(48.dp)
            .then(modifier)
    )
}

data class SectionContent(
    val accessibilityDescription: String,
    val imageModel: Any,
)

@Composable
fun Section(header: String, contents: List<SectionContent>, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
        modifier = Modifier
            .then(modifier)
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
