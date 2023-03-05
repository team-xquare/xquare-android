package com.xquare.xquare_android.webview

import android.webkit.WebView

fun WebView.sendResultOfConfirmModal(
    isConfirmed: Boolean,
) = this.loadUrl(
    "javascript:window.dispatchEvent(new CustomEvent('confirmXBridge', { detail: { success: $isConfirmed }}))"
)

fun WebView.sendResultOfTimePicker(
    id: String,
    time: String
) = this.loadUrl(
    "javascript:window.dispatchEvent(new CustomEvent('timePickerXBridge', { detail: { id: '$id', time: '$time' }}))"
)
