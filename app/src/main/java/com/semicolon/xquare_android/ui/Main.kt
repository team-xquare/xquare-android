package com.semicolon.xquare_android.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.semicolon.xquare_android.ui.navigation.BottomNavigationItem

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
            composable(BottomNavigationItem.Home.route) {  }
        }
    }
}