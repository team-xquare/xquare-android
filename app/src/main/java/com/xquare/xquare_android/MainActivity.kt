package com.xquare.xquare_android

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xquare.xquare_android.component.BottomNavigation
import com.xquare.xquare_android.feature.home.HomeScreen
import com.xquare.xquare_android.feature.splash.SplashScreen
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.navigation.BottomNavigationItem
import com.xquare.xquare_android.theme.XquareandroidTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        super.onCreate(savedInstanceState)
        setContent {
            XquareandroidTheme {
                BaseApp()
            }
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
        composable(AppNavigationItem.Main.route) {
            Main(navController)
        }
    }
}

@Composable
fun Main(mainNavController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomNavigation(
                navController = navController,
                items = listOf(
                    BottomNavigationItem.Home,
                    BottomNavigationItem.Schedule,
                    BottomNavigationItem.Feed,
                    BottomNavigationItem.Apply,
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
            composable(BottomNavigationItem.Apply.route) {
                // TODO()
            }
            composable(BottomNavigationItem.All.route) {
                // TODO()
            }
        }
    }
}