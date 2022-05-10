package com.semicolon.xquare_android.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.semicolon.xquare_android.ui.navigation.AppNavigationItem

@Composable
fun BaseApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppNavigationItem.Main.route) {
        composable(AppNavigationItem.Main.route) {
            Main(navController)
        }
    }
}