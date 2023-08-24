package com.xquare.xquare_android.feature.webview

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.webkit.CookieManager
import android.webkit.WebView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.semicolon.design.Body3
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.ActionSheet
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.component.Header
import com.xquare.xquare_android.component.WebView
import com.xquare.xquare_android.component.modal.ConfirmModal
import com.xquare.xquare_android.component.modal.PeriodPickerModal
import com.xquare.xquare_android.component.modal.TimePickerDialog
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast
import com.xquare.xquare_android.util.parseBitmap
import com.xquare.xquare_android.util.toBase64
import com.xquare.xquare_android.util.updateUi
import com.xquare.xquare_android.webview.WebToAppBridge
import com.xquare.xquare_android.webview.data.ActionSheetInfo
import com.xquare.xquare_android.webview.data.ModalInfo
import com.xquare.xquare_android.webview.data.PeriodPickerInfo
import com.xquare.xquare_android.webview.data.PhotoPickerInfo
import com.xquare.xquare_android.webview.data.RightButtonEnabled
import com.xquare.xquare_android.webview.data.TimePickerInfo
import com.xquare.xquare_android.webview.sendImagesOfPhotoPicker
import com.xquare.xquare_android.webview.sendIndexOfActionSheet
import com.xquare.xquare_android.webview.sendResultOfConfirmModal
import com.xquare.xquare_android.webview.sendResultOfPeriodPicker
import com.xquare.xquare_android.webview.sendResultOfRightButton
import com.xquare.xquare_android.webview.sendResultOfTimePicker
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
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
    var timePickerState: TimePickerInfo? by remember { mutableStateOf(null) }
    var periodPickerState: PeriodPickerInfo? by remember { mutableStateOf(null) }
    var headers: Map<String, String> by remember { mutableStateOf(mapOf()) }
    val viewModel: WebViewViewModel = hiltViewModel()
    val actionSheetScope = rememberCoroutineScope()
    val actionSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var actionSheetInfo: ActionSheetInfo? by remember { mutableStateOf(null) }
    var actionSheetIndex: Int? by remember { mutableStateOf(null) }
    var galleryState: PhotoPickerInfo? by remember { mutableStateOf(null) }
    val photos: ArrayList<String> = ArrayList()
    var isRightButtonEnabled: RightButtonEnabled by remember {
        mutableStateOf(RightButtonEnabled(false))
    }
    var keyboardState by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val bridge = WebToAppBridge(
        onNavigate = {
            val targetUrl = url + it.url
            updateUi { _ ->
                navController.navigate(
                    AppNavigationItem.CommonWebView.createRoute(
                        targetUrl,
                        it.title,
                        it.rightButtonText
                    )
                )
            }
        },
        onImageDetail = { images ->
            updateUi { navController.navigate(AppNavigationItem.ImageDetail.createRoute(images)) }
        },
        onConfirmModal = {
            if (!keyboardState) {
                modalState = it
            }
        },
        onBack = { updateUi { navController.popBackStack() } },
        onError = { makeToast(context, it.message) },
        onSuccess = { makeToast(context, it.message) },
        onPhotoPicker = {
            makeToast(context, "사진은 10장까지 선택할 수 있습니다.")
            galleryState = it
        },
        onTimePicker = {
            if (!keyboardState) {
                timePickerState = it
            }
        },
        onPeriodPicker = { periodPickerState = it },
        onActionSheet = {
            actionSheetInfo = it
            actionSheetScope.launch {
                actionSheetState.show()
            }
        },
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
    LaunchedEffect(actionSheetState.isVisible) {
        if (!actionSheetState.isVisible) {
            actionSheetIndex?.run {
                webView?.sendIndexOfActionSheet(actionSheetInfo!!.id, this)
            }
            actionSheetInfo = null
        }
    }

    modalState?.let {
        ConfirmModal(
            message = it.message,
            confirmText = it.confirmText,
            cancelText = it.cancelText,
            onConfirm = {
                webView?.sendResultOfConfirmModal(it.id, true)
                modalState = null
            },
            onCancel = {
                webView?.sendResultOfConfirmModal(it.id, false)
                modalState = null
            }
        )
    }
    timePickerState?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TimePickerDialog(
                defaultTime = it.time,
                onCancel = { timePickerState = null },
                onConfirm = { time ->
                    webView?.sendResultOfTimePicker(it.id, time)
                    timePickerState = null
                }
            )
        } else {
            makeToast(context, "안드로이드 버전이 낮아 사용할 수 없습니다.")
        }
    }
    periodPickerState?.let {
        PeriodPickerModal(
            defaultPeriod = it.period,
            onCancel = { periodPickerState = null },
            onConfirm = { period ->
                webView?.sendResultOfPeriodPicker(it.id, period)
                periodPickerState = null
            }
        )
    }
    val openWebViewGallery =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data!!.clipData?.run {
                    for (i in 0 until itemCount) {
                        val listItem = getItemAt(i).uri.parseBitmap(context).toBase64()
                            .replace("\\r\\n|\\r|\\n|\\n\\r".toRegex(), "")
                        photos.add("'data:image/png;base64,${listItem}'")
                    }
                    webView?.sendImagesOfPhotoPicker(galleryState!!.id, photos)
                    photos.clear()
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
    ActionSheet(
        state = actionSheetState,
        list = actionSheetInfo?.menu ?: listOf(),
        onClick = {
            actionSheetIndex = it
            actionSheetScope.launch {
                actionSheetState.hide()
            }
        }
    ) {
        CommonWebView(
            haveBackButton = haveBackButton,
            title = title,
            url = url,
            rightButtonText = rightButtonText,
            rightButtonEnabled = isRightButtonEnabled.isEnabled,
            bridges = mapOf(Pair("webview", bridge)),
            onBackClick = { navController.popBackStack() },
            onTextBtnClick = { webView?.sendResultOfRightButton() },
            keyboardCheck = { keyboardState = it },
            onWebViewCreate = {
                webView = it
                CookieManager.getInstance().apply {
                    setAcceptCookie(true)
                    setAcceptThirdPartyCookies(it, true)
                }
            }
        )
    }

}

@Composable
private fun CommonWebView(
    haveBackButton: Boolean,
    title: String,
    url: String,
    rightButtonText: String?,
    rightButtonEnabled: Boolean,
    bridges: Map<String, Any>,
    onBackClick: () -> Unit,
    onTextBtnClick: () -> Unit,
    keyboardCheck: (Boolean) -> Unit,
    onWebViewCreate: (WebView) -> Unit,
) {
    val appBarUrlList = listOf(
        "https://service.xquare.app/xbridge-test",
        "https://prod-server.xquare.app/feed",
        "https://prod-server.xquare.app/apply",
    )

    val context = LocalContext.current
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = manager.activeNetwork

    Column(
        modifier = Modifier
            .background(color = white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                )
    ) {
        if (appBarUrlList.contains(url)) {
            AppBar(
                painter = if (haveBackButton) painterResource(R.drawable.ic_back) else null,
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

        if (networkInfo != null) {
            WebView(
                url = url,
                bridges = bridges,
                keyboardCheck = keyboardCheck,
                onCreate = onWebViewCreate
            )
        } else {
            Body3(
                text = "네트워크 연결 상태를 확인해주세요.",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center)
            )
        }
    }
}
