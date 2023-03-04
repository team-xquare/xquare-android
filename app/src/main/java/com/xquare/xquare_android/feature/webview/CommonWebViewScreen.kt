package com.xquare.xquare_android.feature.webview

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.webkit.WebView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.xquare.xquare_android.util.parseBitmap
import com.xquare.xquare_android.util.toBase64
import com.xquare.xquare_android.util.updateUi
import com.xquare.xquare_android.webview.data.ModalInfo
import com.xquare.xquare_android.webview.WebToAppBridge
import com.xquare.xquare_android.webview.data.PhotoPickerInfo
import com.xquare.xquare_android.webview.sendImagesOfPhotoPicker
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
    var headers: Map<String, String> by remember { mutableStateOf(mapOf()) }
    var galleryState: PhotoPickerInfo? by remember { mutableStateOf(null) }
    val photos: ArrayList<String> = ArrayList()
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
        onPhotoPicker = { galleryState = it },
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
        onWebviewCreate = { webView = it },
    )
    val openWebViewGallery =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
             if (result.resultCode == RESULT_OK) {
                 result.data!!.clipData?.run {
                    if (itemCount > 10) {
                        makeToast(context, "사진은 10장까지 선택할 수 있습니다.")
                    } else {
                        for (i in 0 until itemCount) {
                            val listItem = getItemAt(i).uri.parseBitmap(context).toBase64().replace("\\r\\n|\\r|\\n|\\n\\r".toRegex(),"")
                            photos.add("'data:image/png;base64,${listItem}'")
                        }
                        webView?.sendImagesOfPhotoPicker(galleryState!!.id,photos)
                        photos.clear()
                    }
                }
            }
            galleryState = null
        }
    val openGalleryLauncher =
        Intent(Intent.ACTION_PICK).apply {
            this.type = "image/*"
            this.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }

    galleryState?.run {
        openWebViewGallery.launch(openGalleryLauncher)
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