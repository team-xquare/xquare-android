package com.xquare.xquare_android.feature.splash

import com.xquare.domain.usecase.auth.AutoSignInUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val autoSignInUseCase: AutoSignInUseCase
) : BaseViewModel<SplashViewModel.Event>() {

    fun autoLogin() = execute(
        job = { autoSignInUseCase.execute(Unit) },
        onSuccess = { emitEvent(Event.AutoLoginSuccess) },
        onFailure = { emitEvent(Event.AutoLoginFailure) }
    )

    sealed class Event {
        object AutoLoginSuccess : Event()
        object AutoLoginFailure : Event()
    }
}