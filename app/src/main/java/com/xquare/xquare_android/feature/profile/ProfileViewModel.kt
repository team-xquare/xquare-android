package com.xquare.xquare_android.feature.profile

import com.xquare.domain.entity.profile.ProfileEntity
import com.xquare.domain.usecase.user.FetchProfileUseCase
import com.xquare.domain.usecase.user.FixProfileImageUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchProfileUseCase: FetchProfileUseCase,
    private val fixProfileImageUseCase: FixProfileImageUseCase,
) : BaseViewModel<ProfileViewModel.Event>() {

    fun fetchProfile() =
        execute(
            job = { fetchProfileUseCase.execute(Unit) },
            onSuccess = { it.collect { profile -> emitEvent(Event.Success(profile)) } },
            onFailure = { emitEvent(Event.Failure) }
        )

    fun fixProfileImage(image: String?) {
        execute(
            job = { fixProfileImageUseCase.execute(image) },
            onSuccess = { emitEvent(Event.ImageChangeSuccess) },
            onFailure = { emitEvent(Event.ImageChangeFailure) }
        )
    }

    sealed class Event {
        data class Success(val data: ProfileEntity) : Event()
        object Failure : Event()
        object ImageChangeSuccess : Event()
        object ImageChangeFailure : Event()
    }
}