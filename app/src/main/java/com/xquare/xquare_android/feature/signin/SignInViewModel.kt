package com.xquare.xquare_android.feature.signin

import com.xquare.domain.entity.auth.SignInEntity
import com.xquare.domain.exception.*
import com.xquare.domain.usecase.auth.FetchIdUseCase
import com.xquare.domain.usecase.auth.SignInUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val fetchIdUseCase: FetchIdUseCase,
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

    fun fetchId() = execute(
        job = { fetchIdUseCase.execute(Unit) },
        onSuccess = { emitEvent(Event.FetchIdSuccess(it)) },
        onFailure = {  }
    )

    sealed class Event {
        object Success : SignInViewModel.Event()
        object BadRequest : Event()
        object NotFound : Event()
        object Timeout : Event()
        object TooManyRequest : Event()
        data class FetchIdSuccess(val data: String) : Event()
    }
}