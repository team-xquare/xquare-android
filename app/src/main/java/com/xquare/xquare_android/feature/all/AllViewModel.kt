package com.xquare.xquare_android.feature.all

import com.xquare.domain.usecase.auth.LogoutUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel<AllViewModel.Event>() {

    fun logout() =
        execute(
            job = { logoutUseCase.execute(Unit) },
            onSuccess = { emitEvent(Event.LogoutSuccess) },
            onFailure = {  }
        )

    sealed class Event {
        object LogoutSuccess : Event()
    }
}