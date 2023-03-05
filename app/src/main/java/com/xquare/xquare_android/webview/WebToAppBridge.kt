package com.xquare.xquare_android.webview

import android.webkit.JavascriptInterface
import com.google.gson.Gson
import com.xquare.xquare_android.webview.data.ActionSheetInfo
import com.xquare.xquare_android.webview.data.ImageInfo
import com.xquare.xquare_android.webview.data.ModalInfo
import com.xquare.xquare_android.webview.data.NavigateInfo
import com.xquare.xquare_android.webview.data.PhotoPickerInfo
import com.xquare.xquare_android.webview.data.RightButtonEnabled
import com.xquare.xquare_android.webview.data.WebViewError

class WebToAppBridge(
    val onNavigate: (NavigateInfo) -> Unit = {},
    val onImageDetail: (ImageInfo) -> Unit = {},
    val onConfirmModal: (ModalInfo) -> Unit = {},
    val onBack: () -> Unit = {},
    val onError: (WebViewError) -> Unit = {},
    val onPhotoPicker: (PhotoPickerInfo) -> Unit = {},
    val onActionSheet: (ActionSheetInfo) -> Unit = {},
    val onIsRightButtonEnabled: (RightButtonEnabled) -> Unit = {},
    private val gson: Gson = Gson(),
) {

    @JavascriptInterface
    fun navigate(data: String) =
        onNavigate(gson.fromJson(data, NavigateInfo::class.java))

    @JavascriptInterface
    fun imageDetail(data: String) =
        onImageDetail(gson.fromJson(data, ImageInfo::class.java))

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

    @JavascriptInterface
    fun actionSheet(data: String) =
        onActionSheet(gson.fromJson(data, ActionSheetInfo::class.java))

    @JavascriptInterface
    fun isRightButtonEnabled(data: String) =
        onIsRightButtonEnabled(gson.fromJson(data, RightButtonEnabled::class.java))
}