package com.xquare.xquare_android.webview

import android.webkit.WebView

fun WebView.sendResultOfConfirmModal(
    isConfirmed: Boolean,
) = this.loadUrl(
    "javascript:window.dispatchEvent(new CustomEvent('confirmXBridge', { detail: { success: $isConfirmed }}))"
)

fun WebView.sendImagesOfPhotoPicker(
    id: String,
    photos: List<String>,
) = this.loadUrl(
    "javascript:window.dispatchEvent(new CustomEvent('photoPickerXBridge', { detail: { id: '$id',photos: $photos}}))"
)