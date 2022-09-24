package com.xquare.xquare_android.feature.terms

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.component.WebView

@Composable
fun TermsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            AppBar(
                text = "이용약관",
                painter = painterResource(R.drawable.ic_placeholder),
                onIconClick = { navController.popBackStack() }
            )
        }
    ) {
        WebView(
            modifier = Modifier.padding(it),
            url = "https://team-xquare.github.io/terms/TermsOfService.html"
        )
    }
}