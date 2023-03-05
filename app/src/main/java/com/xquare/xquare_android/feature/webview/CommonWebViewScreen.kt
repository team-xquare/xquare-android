package com.xquare.xquare_android.feature.webview

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.ActionSheet
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.component.ConfirmModal
import com.xquare.xquare_android.component.WebView
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast
import com.xquare.xquare_android.util.updateUi
import com.xquare.xquare_android.webview.data.ModalInfo
import com.xquare.xquare_android.webview.WebToAppBridge
import com.xquare.xquare_android.webview.data.ActionSheetInfo
import com.xquare.xquare_android.webview.sendIndexOfActionSheet
import com.xquare.xquare_android.webview.sendResultOfConfirmModal
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommonWebViewScreen(
    navController: NavController,
    url: String,
    title: String,
    haveBackButton: Boolean,
    changeActionSheetState: (Boolean) -> Unit = {}
) {
    var webView: WebView? by remember { mutableStateOf(null) }
    var modalState: ModalInfo? by remember { mutableStateOf(null) }
    val actionSheetScope = rememberCoroutineScope()
    val actionSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var actionSheetInfo: ActionSheetInfo? by remember { mutableStateOf(null) }
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
        onActionSheet = {
            actionSheetInfo = it
            actionSheetScope.launch {
                actionSheetState.show()
            }
            changeActionSheetState(true)
        },
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
    LaunchedEffect(actionSheetState.isVisible) {
        if (!actionSheetState.isVisible) {
            changeActionSheetState(false)
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

    ActionSheet(
        state = actionSheetState,
        list = actionSheetInfo?.menu ?: listOf(),
        onClick = {
            actionSheetScope.launch {
                actionSheetState.hide()
            }
            changeActionSheetState(false)
            webView?.sendIndexOfActionSheet(actionSheetInfo!!.id, it)
            actionSheetInfo = null
        }
    ) {
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
        if (headers.isNotEmpty()) WebView(
            url = url,
            headers = headers,
            bridges = bridges,
            onCreate = onWebviewCreate
        )
    }
}