package com.xquare.xquare_android.feature.webview

import com.xquare.domain.usecase.webview.FetchAuthorizationHeadersUseCase
import com.xquare.domain.usecase.webview.RefreshAuthorizationHeadersUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val fetchAuthorizationHeadersUseCase: FetchAuthorizationHeadersUseCase,
    private val refreshAuthorizationHeadersUseCase: RefreshAuthorizationHeadersUseCase,
) : BaseViewModel<WebViewViewModel.Event>() {

    fun fetchAuthorizationHeader() =
        execute(
            job = { fetchAuthorizationHeadersUseCase.execute(Unit) },
            onSuccess = { emitEvent(Event.FetchSuccess(it)) },
            onFailure = { emitEvent(Event.NeedToLogin) }
        )

    fun refreshAuthorizationHeader() =
        execute(
            job = { refreshAuthorizationHeadersUseCase.execute(Unit) },
            onSuccess = { emitEvent(Event.RefreshSuccess(it)) },
            onFailure = { emitEvent(Event.NeedToLogin) }
        )

    sealed class Event {
        data class FetchSuccess(val data: Map<String, String>) : Event()
        data class RefreshSuccess(val data: Map<String, String>) : Event()
        object NeedToLogin : Event()
    }
}