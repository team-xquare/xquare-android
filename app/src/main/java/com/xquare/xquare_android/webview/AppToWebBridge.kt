package com.xquare.xquare_android.webview

import android.webkit.WebView

fun WebView.sendResultOfConfirmModal(
    isConfirmed: Boolean,
) = this.loadUrl(
    "javascript:window.dispatchEvent(new CustomEvent('confirmXBridge', { detail: { success: $isConfirmed }}))"
)

fun WebView.sendResultOfRightButton() = this.loadUrl(
    "javascript:window.dispatchEvent(new CustomEvent('rightButtonTapedXBridge', { detail: {}))"
)