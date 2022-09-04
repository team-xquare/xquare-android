package com.xquare.xquare_android.feature.signin

import com.xquare.domain.entity.auth.SignInEntity
import com.xquare.domain.exception.*
import com.xquare.domain.usecase.auth.SignInUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : BaseViewModel<SignInViewModel.Event>() {

    fun signIn(signInEntity: SignInEntity) = execute(
        job = { signInUseCase.execute(signInEntity) },
        onSuccess = { emitEvent(Event.Success) },
        onFailure = {
            when (it) {
                is BadRequestException -> emitEvent(Event.BadRequest)
                is NotFoundException -> emitEvent(Event.NotFound)
                is TimeoutException -> emitEvent(Event.Timeout)
                is TooManyRequestException -> emitEvent(Event.TooManyRequest)
            }
        }
    )

    sealed class Event {
        object Success : SignInViewModel.Event()
        object BadRequest : SignInViewModel.Event()
        object NotFound : SignInViewModel.Event()
        object Timeout : SignInViewModel.Event()
        object TooManyRequest : SignInViewModel.Event()
    }
}