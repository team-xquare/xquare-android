package com.xquare.xquare_android.component

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.xquare.xquare_android.util.DevicePaddings

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

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                if (!bottomPaddingFalseUrlList.contains(url)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        view.rootView.setOnApplyWindowInsetsListener { _, insets ->
                            bottomPadding =
                                if (insets.isVisible(WindowInsets.Type.ime())) { 0.dp }
                                else { DevicePaddings.navigationBarHeightDp.dp }
                            insets.consumeSystemWindowInsets()
                        }
                    }
                }

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object :WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

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