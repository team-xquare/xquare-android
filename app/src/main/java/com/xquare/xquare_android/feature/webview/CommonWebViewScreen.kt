package com.xquare.xquare_android.feature.webview

import android.webkit.CookieManager
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.component.ConfirmModal
import com.xquare.xquare_android.component.WebView
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast
import com.xquare.xquare_android.util.updateUi
import com.xquare.xquare_android.webview.ModalInfo
import com.xquare.xquare_android.webview.WebToAppBridge
import com.xquare.xquare_android.webview.sendResultOfConfirmModal

@Composable
fun CommonWebViewScreen(
    navController: NavController,
    url: String,
    title: String,
    haveBackButton: Boolean,
) {
    var webView: WebView? by remember { mutableStateOf(null) }
    var modalState: ModalInfo? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    val bridge = WebToAppBridge(
        onNavigate = {
            val targetUrl = url + it.url
            updateUi { _ ->
                navController.navigate(
                    AppNavigationItem.CommonWebView.createRoute(targetUrl, it.title))
            }
        },
        onImageDetail = { images ->
            updateUi { navController.navigate(AppNavigationItem.ImageDetail.createRoute(images)) }
        },
        onConfirmModal = { modalState = it },
        onBack = { updateUi { navController.popBackStack() } },
        onError = { makeToast(context, it.message) },
    )
    modalState?.let {
        ConfirmModal(
            message = it.message,
            confirmText = it.confirmText,
            cancelText = it.cancelText,
            onConfirm = {
                webView?.sendResultOfConfirmModal(true)
                modalState = null
            },
            onCancel = {
                webView?.sendResultOfConfirmModal(false)
                modalState = null
            }
        )
    }
    CommonWebView(
        haveBackButton = haveBackButton,
        title = title,
        url = url,
        bridges = mapOf(Pair("webview", bridge)),
        onBackClick = { navController.popBackStack() },
        onWebviewCreate = {
            webView = it
            CookieManager.getInstance().apply {
                setAcceptCookie(true)
                setAcceptThirdPartyCookies(it, true)
            }
        }
    )
}

@Composable
private fun CommonWebView(
    haveBackButton: Boolean,
    title: String,
    url: String,
    bridges: Map<String, Any>,
    onBackClick: () -> Unit,
    onWebviewCreate: (WebView) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp
            )
    ) {
        AppBar(
            painter = if (haveBackButton) painterResource(R.drawable.ic_placeholder) else null,
            text = title,
            onIconClick = onBackClick
        )
        WebView(
            url = url,
            bridges = bridges,
            onCreate = onWebviewCreate
        )
    }
}