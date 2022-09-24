package com.xquare.xquare_android.component

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
@Composable
fun WebView(
    modifier: Modifier = Modifier,
    url: String,
    headers: Map<String, String> = mapOf(),
    bridges: Map<String, Any> = mapOf(),
    onCreate: (WebView) -> Unit = {}
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(false)
                settings.builtInZoomControls = false
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.setSupportMultipleWindows(false)
                settings.domStorageEnabled = true
                bridges.forEach { addJavascriptInterface(it.value, it.key) }
                loadUrl(url, headers)
                onCreate(this)
            }
        },
        modifier = modifier
    )
}