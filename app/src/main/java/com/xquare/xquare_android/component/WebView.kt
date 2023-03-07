package com.xquare.xquare_android.component

import android.annotation.SuppressLint
import android.app.Service
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowInsetsAnimationCompat
import com.xquare.xquare_android.util.DevicePaddings

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
@Composable
fun WebView(
    modifier: Modifier = Modifier,
    url: String,
    headers: Map<String, String> = mapOf(),
    bridges: Map<String, Any> = mapOf(),
    onCreate: (WebView) -> Unit = {}
) {
    val bottomPaddingFalseUrlList = listOf(
        "https://service.xquare.app/feed",
        "https://service.xquare.app/apply",
    )

    val bottomState =
        if (bottomPaddingFalseUrlList.contains(url)) 0.dp
        else DevicePaddings.navigationBarHeightDp.dp
    var bottomPadding by remember { mutableStateOf(bottomState) }

    val view = LocalView.current
//    val currentFocus = LocalView.current
//
//    val keyboardVisibilityObserver = remember {
//        ViewTreeObserver.OnGlobalLayoutListener {
//            val rootView = currentFocus.rootView
//            val heightDiff = rootView?.height?.minus(rootView.height - rootView.rootView.height)
//            bottomPadding =
//                if (heightDiff != null && heightDiff > 300) {
//                    0.dp
//                } else {
//                    DevicePaddings.navigationBarHeightDp.dp
//                }
//        }
//    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    view.rootView.setOnApplyWindowInsetsListener { _, insets ->
                        bottomPadding =
                            if (insets.isVisible(WindowInsets.Type.ime())) { 0.dp }
                            else { DevicePaddings.navigationBarHeightDp.dp }
                        insets.consumeSystemWindowInsets()
                    }
                }
              //  viewTreeObserver.addOnGlobalLayoutListener(keyboardVisibilityObserver)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object :WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        CookieManager.getInstance().flush()
                    }
                }
                webChromeClient = WebChromeClient()
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(false)
                settings.builtInZoomControls = false
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.setSupportMultipleWindows(false)
                settings.domStorageEnabled = true
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                bridges.forEach { addJavascriptInterface(it.value, it.key) }
                loadUrl(url, headers)
                onCreate(this)
            }
        },
        modifier = modifier
            .padding(bottom = bottomPadding)
            .imePadding()
    )
}