package com.xquare.xquare_android.webview

import android.webkit.JavascriptInterface
import com.google.gson.Gson
import com.xquare.xquare_android.webview.data.ModalInfo
import com.xquare.xquare_android.webview.data.NavigateInfo
import com.xquare.xquare_android.webview.data.PhotoPickerInfo
import com.xquare.xquare_android.webview.data.WebViewError

class WebToAppBridge(
    val onNavigate: (NavigateInfo) -> Unit = {},
    val onImageDetail: (Array<String>) -> Unit = {},
    val onConfirmModal: (ModalInfo) -> Unit = {},
    val onBack: () -> Unit = {},
    val onError: (WebViewError) -> Unit = {},
    val onPhotoPicker: (PhotoPickerInfo) -> Unit = {},
    private val gson: Gson = Gson(),
) {

    @JavascriptInterface
    fun navigate(data: String) =
        onNavigate(gson.fromJson(data, NavigateInfo::class.java))

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

    @JavascriptInterface
    fun photoPicker(data: String) =
        onPhotoPicker(gson.fromJson(data, PhotoPickerInfo::class.java))
}