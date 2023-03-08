package com.xquare.xquare_android.feature.profile

import android.util.Log
import com.xquare.domain.entity.profile.ProfileEntity
import com.xquare.domain.exception.BadRequestException
import com.xquare.domain.usecase.attachment.UploadFileUseCase
import com.xquare.domain.usecase.user.FetchProfileUseCase
import com.xquare.domain.usecase.user.FixProfileImageUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchProfileUseCase: FetchProfileUseCase,
    private val fixProfileImageUseCase: FixProfileImageUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
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
            onSuccess = { emitEvent(Event.UploadFileSuccess(it.file_url)) },
            onFailure = { emitEvent(Event.UploadFileFailure) }
        )
    }

    sealed class Event {
        data class Success(val data: ProfileEntity) : Event()
        object Failure : Event()

        data class UploadFileSuccess(val data: List<String>) : Event()
        object UploadFileFailure : Event()

        object ImageChangeSuccess : Event()
        object ImageChangeFailure : Event()
    }
}