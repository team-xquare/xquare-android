package com.xquare.xquare_android.feature.webview

import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.component.ConfirmModal
import com.xquare.xquare_android.component.WebView
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.makeToast
import com.xquare.xquare_android.util.updateUi
import com.xquare.xquare_android.webview.ModalInfo
import com.xquare.xquare_android.webview.WebToAppBridge
import com.xquare.xquare_android.webview.sendResultOfConfirmModal
import kotlinx.coroutines.flow.collect

@Composable
fun CommonWebViewScreen(
    navController: NavController,
    url: String,
    title: String,
    haveBackButton: Boolean,
) {
    var webView: WebView? by remember { mutableStateOf(null) }
    var modalState: ModalInfo? by remember { mutableStateOf(null) }
    var headers: Map<String, String> by remember { mutableStateOf(mapOf()) }
    val viewModel: WebViewViewModel = hiltViewModel()
    val context = LocalContext.current
    val bridge = WebToAppBridge(
        onNavigate = {
            val targetUrl = url + it
            updateUi {
                navController.navigate(
                    AppNavigationItem.CommonWebView.createRoute(targetUrl, title))
            }
        },
        onImageDetail = { /* TODO("이미지 상세 페이지로 이동") */ },
        onConfirmModal = { modalState = it },
        onBack = { updateUi { navController.popBackStack() } },
        onError = { makeToast(context, it.message) },
    )
    LaunchedEffect(Unit) {
        viewModel.fetchAuthorizationHeader()
        viewModel.eventFlow.collect {
            when (it) {
                is WebViewViewModel.Event.FetchSuccess -> {
                    println(it.data)
                    headers = it.data
                }
                is WebViewViewModel.Event.RefreshSuccess -> {
                    headers = it.data
                }
                is WebViewViewModel.Event.NeedToLogin -> {
                    navController.navigate(AppNavigationItem.Onboard.route) { popUpTo(0) }
                }
            }
        }
    }
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
        headers = headers,
        bridges = mapOf(Pair("webview", bridge)),
        onBackClick = { navController.popBackStack() },
        onWebviewCreate = { webView = it }
    )
}

@Composable
private fun CommonWebView(
    haveBackButton: Boolean,
    title: String,
    url: String,
    headers: Map<String, String>,
    bridges: Map<String, Any>,
    onBackClick: () -> Unit,
    onWebviewCreate: (WebView) -> Unit,
) {
    Column(
        Modifier.background(color = white)
    ) {
        AppBar(
            painter = if (haveBackButton) painterResource(R.drawable.ic_placeholder) else null,
            text = title,
            onIconClick = onBackClick
        )
        if (headers.isNotEmpty()) WebView(
            url = url,
            headers = headers,
            bridges = bridges,
            onCreate = onWebviewCreate
        )
    }
}