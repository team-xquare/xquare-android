package com.xquare.xquare_android.feature.profile

import com.xquare.domain.entity.profile.ProfileEntity
import com.xquare.domain.usecase.attachment.UploadFileUseCase
import com.xquare.domain.usecase.auth.LogoutUseCase
import com.xquare.domain.usecase.user.FetchProfileUseCase
import com.xquare.domain.usecase.user.FixProfileImageUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchProfileUseCase: FetchProfileUseCase,
    private val fixProfileImageUseCase: FixProfileImageUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<ProfileViewModel.Event>() {

    private val _profile = MutableStateFlow(
        ProfileEntity(
            "", "", "",1,1,1,""
        )
    )
    val profile: StateFlow<ProfileEntity> = _profile

    fun fetchProfile() =
        execute(
            job = { fetchProfileUseCase.execute(Unit) },
            onSuccess = { it.collect { profile -> _profile.tryEmit(profile)} },
            onFailure = {  }
        )

    fun fixProfileImage(image: String?) {
        execute(
            job = { fixProfileImageUseCase.execute(image) },
            onSuccess = { emitEvent(Event.ImageChangeSuccess) },
            onFailure = {  }
        )
    }

    fun uploadFile(file: File) {
        execute(
            job = { uploadFileUseCase.execute(file) },
            onSuccess = { emitEvent(Event.UploadFileSuccess(it.file_url)) },
            onFailure = {  }
        )
    }

    fun logout() =
        execute(
            job = { logoutUseCase.execute(Unit) },
            onSuccess = { emitEvent(Event.LogoutSuccess) },
            onFailure = {  }
        )

    sealed class Event {

        object LogoutSuccess : Event()
        data class Success(val data: ProfileEntity) : Event()
        object Failure : Event()

        data class UploadFileSuccess(val data: List<String>) : Event()

        object ImageChangeSuccess : Event()
    }
}