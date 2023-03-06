package com.xquare.xquare_android.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class AppNavigationItem(val route: String) {

    object Splash : AppNavigationItem("splash")

    object Onboard : AppNavigationItem("onboard")

    object PrivacyPolicy : AppNavigationItem("privacyPolicy")

    object TermsOfService : AppNavigationItem("termsOfService")

    object SignUp : AppNavigationItem("signup")

    object SignIn : AppNavigationItem("signin")

    object Main : AppNavigationItem("main")

    object AllMeal : AppNavigationItem("allMeal")

    object PointHistory : AppNavigationItem("pointHistory")

    object Profile : AppNavigationItem("Profile")

    object Bug : AppNavigationItem("Bug")

    object CommonWebView : AppNavigationItem("commonWebView/{encodedUrl}/{title}") {
        fun createRoute(url: String, title: String): String {
            val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
            return "commonWebView/$encodedUrl/$title"
        }
    }

    object ImageDetail : AppNavigationItem("imageDetail/{joinedEncodedImage}") {
        fun createRoute(images: Array<String>): String {
            val joinedEncodedImage = images.joinToString(",") {
                URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
            }
            return "imageDetail/$joinedEncodedImage"
        }
    }
}