package com.xquare.xquare_android.feature.imagedetail

import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.semicolon.design.color.primary.black.black
import com.semicolon.design.color.primary.dark.dark200
import com.semicolon.design.color.primary.purple.purple400
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun ImageDetailScreen(
    navController: NavController,
    images: Array<String>,
) {
    val context = LocalContext.current
    val onBack: () -> Unit = {
        context.setLightStatusBar()
        context.setLightNavigationBar()
        navController.popBackStack()
    }
    LaunchedEffect(Unit) {
        context.setDarkStatusBar()
        context.setDarkNavigationBar()
    }
    BackHandler(onBack = onBack)
    ImageDetail(
        images = images,
        onBackClick = onBack
    )
}

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageDetail(images: Array<String>, onBackClick: () -> Unit) {
    val pagerState = rememberPagerState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp
            ),
        topBar = {
            AppBar(
                painter = painterResource(R.drawable.ic_back),
                text = "",
                backgroundColor = black,
                onIconClick = onBackClick
            )
        },
        bottomBar = {
            Box(
                Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .background(black),
                contentAlignment = Alignment.Center
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = purple400,
                    inactiveColor = dark200
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize(),
                count = images.count(),
                state = pagerState,
            ) {
                Image(
                    painter = rememberAsyncImagePainter(images[this.currentPage]),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = black)
                )
            }
        }
    }
}