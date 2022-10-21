package com.xquare.xquare_android.feature.application

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.component.WebView

@Composable
fun ApplicationScreen(navController: NavController) {
    Column {
        AppBar(text = "신청")
        WebView(url = "https://service.xquare.app/apply")
    }
}