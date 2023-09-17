package com.xquare.xquare_android.feature.profile

import com.xquare.domain.entity.profile.ProfileEntity
import com.xquare.domain.usecase.attachment.UploadFileUseCase
import com.xquare.domain.usecase.auth.LogoutUseCase
import com.xquare.domain.usecase.user.FetchProfileUseCase
import com.xquare.domain.usecase.user.FixProfileImageUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchProfileUseCase: FetchProfileUseCase,
    private val fixProfileImageUseCase: FixProfileImageUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val logoutUseCase: LogoutUseCase,
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
            onFailure = {
                emitEvent(Event.ImageChangeFailure)
            }
        )
    }

    fun uploadFile(file: File) {
        execute(
            job = { uploadFileUseCase.execute(file) },
            onSuccess = { emitEvent(Event.UploadFileSuccess(it.first().fileUrls)) },
            onFailure = { emitEvent(Event.UploadFileFailure) }
        )
    }

    fun logout() =
        execute(
            job = { logoutUseCase.execute(Unit) },
            onSuccess = { emitEvent(Event.LogoutSuccess) },
            onFailure = { }
        )

    sealed class Event {

        object LogoutSuccess : Event()
        data class Success(val data: ProfileEntity) : Event()
        object Failure : Event()

        data class UploadFileSuccess(val data: List<String>) : Event()
        object UploadFileFailure : Event()

        object ImageChangeSuccess : Event()
        object ImageChangeFailure : Event()
    }
}