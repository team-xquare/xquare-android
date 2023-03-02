package com.xquare.xquare_android.feature.webview

import android.util.Log
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
import com.xquare.xquare_android.component.Header
import com.xquare.xquare_android.component.WebView
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast
import com.xquare.xquare_android.util.updateUi
import com.xquare.xquare_android.webview.data.ModalInfo
import com.xquare.xquare_android.webview.WebToAppBridge
import com.xquare.xquare_android.webview.data.ActionSheetInfo
import com.xquare.xquare_android.webview.data.PhotoPickerInfo
import com.xquare.xquare_android.webview.data.RightButtonEnabled
import com.xquare.xquare_android.webview.data.TimePickerInfo
import com.xquare.xquare_android.webview.sendResultOfConfirmModal
import com.xquare.xquare_android.webview.sendResultOfRightButton

@Composable
fun CommonWebViewScreen(
    navController: NavController,
    url: String,
    title: String,
    rightButtonText: String? = null,
    haveBackButton: Boolean,
) {
    var webView: WebView? by remember { mutableStateOf(null) }
    var modalState: ModalInfo? by remember { mutableStateOf(null) }
    var photoPickerState: PhotoPickerInfo? by remember { mutableStateOf(null) }
    var actionSheetState: ActionSheetInfo? by remember { mutableStateOf(null) }
    var timePickerState: TimePickerInfo? by remember { mutableStateOf(null) }
    var isRightButtonEnabled: RightButtonEnabled by remember { mutableStateOf(RightButtonEnabled(false)) }
    var headers: Map<String, String> by remember { mutableStateOf(mapOf()) }
    val viewModel: WebViewViewModel = hiltViewModel()
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
        onPhotoPicker = { photoPickerState = it },
        onActionSheet = { actionSheetState = it },
        onTimePicker = { timePickerState = it },
        onIsRightButtonEnabled = { isRightButtonEnabled = it },
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

    photoPickerState?.let {
    }

    timePickerState?.let {

    }

    CommonWebView(
        haveBackButton = haveBackButton,
        title = title,
        url = url,
        rightButtonText = rightButtonText,
        rightButtonEnabled = isRightButtonEnabled.isEnabled,
        headers = headers,
        bridges = mapOf(Pair("webview", bridge)),
        onBackClick = { navController.popBackStack() },
        onTextBtnClick = { webView?.sendResultOfRightButton() },
        onWebviewCreate = {
            webView = it
        }
    )
}

@Composable
private fun CommonWebView(
    haveBackButton: Boolean,
    title: String,
    url: String,
    rightButtonText: String?,
    rightButtonEnabled: Boolean,
    headers: Map<String, String>,
    bridges: Map<String, Any>,
    onBackClick: () -> Unit,
    onTextBtnClick: () -> Unit,
    onWebviewCreate: (WebView) -> Unit,
) {
    val urlList = listOf(
        "https://service.xquare.app/feed",
        "https://service.xquare.app/apply",
        "https://service.xquare.app/xbridge-test"
    )
    Column(
        modifier = Modifier
            .background(color = white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp
            )
    ) {
        if (urlList.contains(url)) {
            AppBar(
                painter = if (haveBackButton) painterResource(R.drawable.ic_placeholder) else null,
                text = title,
                onIconClick = onBackClick
            )
        } else {
            Header(
                painter = painterResource(id = R.drawable.ic_back),
                title = title,
                btnText = rightButtonText,
                btnEnabled = rightButtonEnabled,
                onIconClick = onBackClick,
                onBtnClick = onTextBtnClick,
            )
        }

        if (headers.isNotEmpty()) WebView(
            url = url,
            headers = headers,
            bridges = bridges,
            onCreate = onWebviewCreate
        )
    }
}