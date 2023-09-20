package com.xquare.xquare_android.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.semicolon.design.color.primary.purple.purple400
import com.xquare.xquare_android.R
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.setLightNavigationBar
import com.xquare.xquare_android.util.setLightStatusBar

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val splashViewModel: SplashViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        splashViewModel.autoLogin()
        splashViewModel.eventFlow.collect {
            context.setLightStatusBar()
            context.setLightNavigationBar()
            when (it) {
                SplashViewModel.Event.AutoLoginSuccess ->
                    navController.navigate(AppNavigationItem.Main.route) { popUpTo(0) }
                SplashViewModel.Event.AutoLoginFailure -> {
                    navController.navigate(AppNavigationItem.Onboard.route) { popUpTo(0) }
                }
            }
        }
    }
    Splash()
}

@Composable
private fun Splash() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(purple400),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.img_big_logo),
            contentDescription = null,
            modifier = Modifier.size(240.dp),
            tint = Color.Unspecified
        )
    }
}