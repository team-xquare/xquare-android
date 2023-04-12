package com.xquare.xquare_android.navigation

import com.google.gson.Gson
import com.xquare.domain.entity.schedules.SchedulesEntity
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

    object Alarm : AppNavigationItem("alarm")

    object PointHistory : AppNavigationItem("pointHistory")

    object Profile : AppNavigationItem("Profile")

    object Bug : AppNavigationItem("Bug")

    object Pass : AppNavigationItem("Pass")

    object WriteSchedule : AppNavigationItem("writeSchedule/{schedulesData}") {
        fun createRoute(schedulesData: SchedulesEntity.SchedulesDataEntity?): String {
            return if (schedulesData == null) "writeSchedule/null" else "writeSchedule/${
                Gson().toJson(schedulesData,
                    SchedulesEntity.SchedulesDataEntity::class.java)
            }"
        }
    }

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

    object WebViewTest : AppNavigationItem("WebViewTest")
}