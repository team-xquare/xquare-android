package com.xquare.xquare_android.navigation

import android.util.Log
import com.xquare.xquare_android.webview.data.ImageInfo
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

    object Director : AppNavigationItem("Director")


    object CommonWebView : AppNavigationItem("commonWebView/{encodedUrl}/{title}/{rightButtonText}") {
        fun createRoute(url: String, title: String, rightButtonText: String?): String {
            val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
            return "commonWebView/$encodedUrl/$title/$rightButtonText"
        }
    }

    object ImageDetail : AppNavigationItem("imageDetail/{joinedEncodedImage}") {
        fun createRoute(imageInfo: ImageInfo): String {
            val joinedEncodedImage = imageInfo.images.joinToString(",") {
                URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
            }
            return "imageDetail/$joinedEncodedImage"
        }
    }
}