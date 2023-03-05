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

fun WebView.sendIndexOfActionSheet(
    id: String,
    index: Int
) = this.loadUrl(
    "javascript:window.dispatchEvent(new CustomEvent('actionSheetXBridge', { detail: { id: '$id', index: $index }}))"
)

fun WebView.sendResultOfRightButton() = this.loadUrl(
    "javascript:window.dispatchEvent(new CustomEvent('rightButtonTapedXBridge', { detail: {}))"
)