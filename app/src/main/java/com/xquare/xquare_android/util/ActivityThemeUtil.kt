package com.xquare.xquare_android.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.view.WindowCompat

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

fun Int.pixelToDp(context: Context): Int =
    (this / context.resources.displayMetrics.density).toInt()

object DevicePaddings {
    var statusBarHeightDp: Int = 0
    var navigationBarHeightDp: Int = 0
}