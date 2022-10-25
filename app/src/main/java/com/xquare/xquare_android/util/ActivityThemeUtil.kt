package com.xquare.xquare_android.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.view.WindowCompat
import com.xquare.xquare_android.getActivity

fun Activity.setStatusBarTransparent() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
    if (Build.VERSION.SDK_INT >= 30)
        WindowCompat.setDecorFitsSystemWindows(window, false)
}


fun Context.getStatusBarHeightDp(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId).pixelToDp(this)
    else 0
}

fun Context.getNavigationBarHeightDp(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId).pixelToDp(this)
    else 0
}

fun Context.setLightStatusBar() {
    getActivity()?.window?.let { window ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
        Unit
    }
}

fun Context.setLightNavigationBar() {
    getActivity()?.window?.let { window ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            )
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        }
        Unit
    }
}

fun Context.setDarkStatusBar() {
    getActivity()?.window?.let { window ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = 0
            }
        }
        Unit
    }
}

fun Context.setDarkNavigationBar() {
    getActivity()?.window?.let { window ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                0, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            )
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.decorView.systemUiVisibility = 0
            }
        }
        Unit
    }
}


fun Int.pixelToDp(context: Context): Int =
    (this / context.resources.displayMetrics.density).toInt()

object DevicePaddings {
    var statusBarHeightDp: Int = 0
    var navigationBarHeightDp: Int = 0
}