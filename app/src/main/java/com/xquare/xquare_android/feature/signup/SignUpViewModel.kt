package com.xquare.xquare_android.feature.signup

import com.xquare.domain.entity.auth.SignUpEntity
import com.xquare.domain.exception.BadRequestException
import com.xquare.domain.exception.ConflictException
import com.xquare.domain.exception.TimeoutException
import com.xquare.domain.exception.TooManyRequestException
import com.xquare.domain.usecase.auth.SignUpUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel<SignUpViewModel.Event>() {

    fun signUp(signUpEntity: SignUpEntity) = execute(
        job = { signUpUseCase.execute(signUpEntity) },
        onSuccess = { emitEvent(Event.Success) },
        onFailure = {
            when (it) {
                is BadRequestException -> emitEvent(Event.BadRequest)
                is TimeoutException -> emitEvent(Event.Timeout)
                is ConflictException -> emitEvent(Event.ConflictAccountId)
                is TooManyRequestException -> emitEvent(Event.TooManyRequest)
            }
        }
    )

    sealed class Event {
        object Success : Event()
        object BadRequest : Event()
        object Timeout : Event()
        object ConflictAccountId : Event()
        object TooManyRequest : Event()
    }
}