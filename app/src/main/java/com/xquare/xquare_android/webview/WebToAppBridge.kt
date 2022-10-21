package com.xquare.xquare_android.webview

import android.webkit.JavascriptInterface
import com.google.gson.Gson

class WebToAppBridge(
    val onNavigate: (String) -> Unit,
    val onImageDetail: (Array<String>) -> Unit,
    val onConfirmModal: (ModalInfo) -> Unit,
    val onBack: () -> Unit,
    val onError: (WebViewError) -> Unit,
    private val gson: Gson = Gson(),
) {

    @JavascriptInterface
    fun navigate(data: String) =
        onNavigate(data)

    @JavascriptInterface
    fun imageDetail(data: String) =
        onImageDetail(gson.fromJson(data, Array<String>::class.java))

    @JavascriptInterface
    fun back(data: Boolean) =
        onBack()

    @JavascriptInterface
    fun confirm(modalInfo: String) =
        onConfirmModal(gson.fromJson(modalInfo, ModalInfo::class.java))

    @JavascriptInterface
    fun error(error: String) =
        onError(gson.fromJson(error, WebViewError::class.java))
}