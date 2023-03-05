package com.xquare.xquare_android.feature.profile

import com.xquare.domain.entity.profile.ProfileEntity
import com.xquare.domain.usecase.user.FetchProfileUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchProfileUseCase: FetchProfileUseCase,
) : BaseViewModel<ProfileViewModel.Event>() {

    fun fetchProfile() =
        execute(
            job = { fetchProfileUseCase.execute(Unit) },
            onSuccess = { it.collect { profile -> emitEvent(Event.Success(profile)) } },
            onFailure = { emitEvent(Event.Failure) }
        )

    sealed class Event {
        data class Success(val data: ProfileEntity) : Event()
        object Failure : Event()
    }
}