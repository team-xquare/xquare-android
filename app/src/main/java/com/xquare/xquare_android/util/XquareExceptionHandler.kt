package com.xquare.xquare_android.util

import android.content.Context
import androidx.navigation.NavController
import com.xquare.domain.exception.NeedLoginException
import com.xquare.domain.exception.NoInternetException
import com.xquare.xquare_android.navigation.AppNavigationItem

private const val InternetErrorMsg = "인터넷 연결상태가 불안정합니다.\n네트워크를 확인해주세요."
private const val LoginErrorMsg = "유저정보가 확인되지 않습니다\n다시 로그인 해주세요."
private const val UnknownErrorMsg = "오류가 발생했습니다\n전체 탭에 버그 제보를 이용해주세요."

class XquareExceptionHandler(
    private val context: Context,
    private val navController: NavController
) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        when (e) {
            is NoInternetException -> makeToast(context, InternetErrorMsg)
            is NeedLoginException -> makeToast(context, LoginErrorMsg)
            else -> {
                makeToast(context, UnknownErrorMsg)
                navController.navigate(AppNavigationItem.Onboard.route) {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }
        }
    }
}