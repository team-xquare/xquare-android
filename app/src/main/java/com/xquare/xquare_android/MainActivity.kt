package com.xquare.xquare_android

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.component.BottomNavigation
import com.xquare.xquare_android.feature.alarm.AlarmScreen
import com.xquare.xquare_android.feature.allmeal.AllMealScreen
import com.xquare.xquare_android.feature.home.HomeScreen
import com.xquare.xquare_android.feature.imagedetail.ImageDetailScreen
import com.xquare.xquare_android.feature.onboard.OnboardScreen
import com.xquare.xquare_android.feature.signin.SignInScreen
import com.xquare.xquare_android.feature.signup.SignUpScreen
import com.xquare.xquare_android.feature.splash.SplashScreen
import com.xquare.xquare_android.feature.webview.CommonWebViewScreen
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.navigation.BottomNavigationItem
import com.xquare.xquare_android.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setStatusBarTransparent()
        DevicePaddings.statusBarHeightDp = getStatusBarHeightDp()
        DevicePaddings.navigationBarHeightDp = getNavigationBarHeightDp()
        super.onCreate(savedInstanceState)
        setContent {
            BaseApp()
        }
    }
}

@Composable
fun BaseApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppNavigationItem.Splash.route) {
        composable(AppNavigationItem.Splash.route) {
            SplashScreen(navController)
        }
        composable(AppNavigationItem.Onboard.route) {
            OnboardScreen(navController)
        }
        composable(AppNavigationItem.PrivacyPolicy.route) {
            CommonWebViewScreen(
                navController = navController,
                url = "https://team-xquare.github.io/terms/PrivacyPolicy.html",
                title = "개인정보처리방침",
                haveBackButton = true
            )
        }
        composable(AppNavigationItem.TermsOfService.route) {
            CommonWebViewScreen(
                navController = navController,
                url = "https://team-xquare.github.io/terms/TermsOfService.html",
                title = "이용약관",
                haveBackButton = true
            )
        }
        composable(AppNavigationItem.SignUp.route) {
            SignUpScreen(navController)
        }
        composable(AppNavigationItem.SignIn.route) {
            SignInScreen(navController)
        }
        composable(AppNavigationItem.Main.route) {
            Main(navController)
        }
        composable(AppNavigationItem.AllMeal.route) {
            AllMealScreen(navController)
        }
        composable(AppNavigationItem.Alarm.route) {
            AlarmScreen(navController)
        }
        composable(AppNavigationItem.CommonWebView.route) {
            val encodedUrl = it.arguments!!["encodedUrl"].toString()
            val title = it.arguments!!["title"].toString()
            val url = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())
            CommonWebViewScreen(
                navController = navController,
                url = url,
                title = title,
                haveBackButton = true
            )
        }
        composable(AppNavigationItem.ImageDetail.route) {
            val joinedEncodedImage = it.arguments!!["joinedEncodedImage"].toString()
            val images = joinedEncodedImage.split(",")
                .map { encoded -> URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString()) }
                .toTypedArray()
            ImageDetailScreen(navController, images)
        }
    }
}

@Composable
fun Main(mainNavController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    val context = LocalContext.current
    val window = context.getActivity()?.window
    Scaffold(
        modifier = Modifier
            .background(white)
            .padding(
                bottom = DevicePaddings.navigationBarHeightDp.dp
            ),
        scaffoldState = scaffoldState,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomNavigation(
                navController = navController,
                items = listOf(
                    BottomNavigationItem.Home,
                    BottomNavigationItem.Schedule,
                    BottomNavigationItem.Feed,
                    BottomNavigationItem.Application,
                    BottomNavigationItem.All
                )
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavigationItem.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavigationItem.Home.route) {
                HomeScreen(mainNavController)
            }
            composable(BottomNavigationItem.Schedule.route) {
                // TODO()
            }
            composable(BottomNavigationItem.Feed.route) {
                // TODO()
            }
            composable(BottomNavigationItem.Application.route) {
                CommonWebViewScreen(
                    navController = mainNavController,
                    url = "https://service.xquare.app/xbridge-test",
                    title = "신청",
                    haveBackButton = false
                )
            }
            composable(BottomNavigationItem.All.route) {
                // TODO()
            }
        }
    }
}

fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}