package com.semicolon.xquare_android

import android.os.Bundle
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
import com.semicolon.xquare_android.feature.home.HomeScreen
import com.semicolon.xquare_android.navigation.AppNavigationItem
import com.semicolon.xquare_android.navigation.BottomNavigationItem
import com.semicolon.xquare_android.theme.XquareandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
    NavHost(navController = navController, startDestination = AppNavigationItem.Main.route) {
        composable(AppNavigationItem.Main.route) {
            Main(navController)
        }
    }
}

@Composable
fun Main(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val navHostController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        isFloatingActionButtonDocked = true,
        bottomBar = { }
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = BottomNavigationItem.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavigationItem.Home.route) {
                HomeScreen(navController)
            }
        }
    }
}