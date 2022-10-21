package com.xquare.xquare_android.feature.webview

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.component.WebView

@Composable
fun CommonWebViewScreen(
    navController: NavController,
    url: String,
    title: String,
    haveBackButton: Boolean,
) {
    Column {
        AppBar(
            painter = if (haveBackButton) painterResource(R.drawable.ic_placeholder) else null,
            text = title,
            onIconClick = { navController.popBackStack() }
        )
        WebView(url = url)
    }
}