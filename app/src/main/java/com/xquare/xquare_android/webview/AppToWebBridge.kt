package com.xquare.xquare_android.webview

import android.webkit.WebView

fun WebView.sendResultOfConfirmModal(
    id: String,
    isConfirmed: Boolean,
) = this.loadUrl(
    "javascript:window.dispatchEvent(new CustomEvent('confirmXBridge', { detail: { id: '$id', success: $isConfirmed }}))"
)

fun WebView.sendResultOfTimePicker(
    id: String,
    time: String
) = this.loadUrl(
    "javascript:window.dispatchEvent(new CustomEvent('timePickerXBridge', { detail: { id: '$id', time: '$time' }}))"
)

fun WebView.sendResultOfPeriodPicker(
    id: String,
    period: Int
) = this.loadUrl(
    "javascript:window.dispatchEvent(new CustomEvent('periodPickerXBridge', { detail: { id: '$id', period: '$period' }}))"
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
    "javascript:window.dispatchEvent(new CustomEvent('rightButtonTapedXBridge', { detail: {}}))"
)
