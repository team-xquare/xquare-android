package com.xquare.xquare_android

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xquare.xquare_android.component.BottomNavigation
import com.xquare.xquare_android.feature.home.HomeScreen
import com.xquare.xquare_android.feature.onboard.OnboardScreen
import com.xquare.xquare_android.feature.privacypolicy.PrivacyPolicyScreen
import com.xquare.xquare_android.feature.signin.SignInScreen
import com.xquare.xquare_android.feature.signup.SignUpScreen
import com.xquare.xquare_android.feature.splash.SplashScreen
import com.xquare.xquare_android.feature.terms.TermsScreen
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
            BaseApp()
        }
    }
}

@Composable
fun BaseApp() {
    val context = LocalContext.current
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppNavigationItem.Splash.route) {
        composable(AppNavigationItem.Splash.route) {
            SplashScreen(navController)
        }
        composable(AppNavigationItem.Onboard.route) {
            context.getActivity()?.window?.statusBarColor =
                ContextCompat.getColor(context, R.color.white)
            OnboardScreen(navController)
        }
        composable(AppNavigationItem.PrivacyPolicy.route) {
            context.getActivity()?.window?.statusBarColor =
                ContextCompat.getColor(context, R.color.white)
            PrivacyPolicyScreen(navController)
        }
        composable(AppNavigationItem.TermsOfService.route) {
            context.getActivity()?.window?.statusBarColor =
                ContextCompat.getColor(context, R.color.white)
            TermsScreen(navController)
        }
        composable(AppNavigationItem.SignUp.route) {
            context.getActivity()?.window?.statusBarColor =
                ContextCompat.getColor(context, R.color.white)
            SignUpScreen(navController)
        }
        composable(AppNavigationItem.SignIn.route) {
            context.getActivity()?.window?.statusBarColor =
                ContextCompat.getColor(context, R.color.white)
            SignInScreen(navController)
        }
        composable(AppNavigationItem.Main.route) {
            context.getActivity()?.window?.statusBarColor =
                ContextCompat.getColor(context, R.color.white)
            Main(navController)
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
                window?.statusBarColor = ContextCompat.getColor(context, R.color.gray50)
            }
            composable(BottomNavigationItem.Schedule.route) {
                // TODO()
                window?.statusBarColor = ContextCompat.getColor(context, R.color.white)
            }
            composable(BottomNavigationItem.Feed.route) {
                // TODO()
                window?.statusBarColor = ContextCompat.getColor(context, R.color.white)
            }
            composable(BottomNavigationItem.Apply.route) {
                // TODO()
                window?.statusBarColor = ContextCompat.getColor(context, R.color.white)
            }
            composable(BottomNavigationItem.All.route) {
                // TODO()
                window?.statusBarColor = ContextCompat.getColor(context, R.color.white)
            }
        }
    }
}


fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}