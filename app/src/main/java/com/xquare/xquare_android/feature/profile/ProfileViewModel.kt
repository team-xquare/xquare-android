package com.xquare.xquare_android.feature.profile

import android.util.Log
import com.xquare.domain.entity.github.GithubOAuthEntity
import com.xquare.domain.entity.profile.ProfileEntity
import com.xquare.domain.usecase.attachment.UploadFileUseCase
import com.xquare.domain.usecase.auth.LogoutUseCase
import com.xquare.domain.usecase.github.FetchGithubOAuthCheckUseCase
import com.xquare.domain.usecase.github.FetchGithubOAuthUseCase
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
    private val fetchGithubOAuthUseCase: FetchGithubOAuthUseCase,
    private val fetchGithubOAuthCheckUseCase: FetchGithubOAuthCheckUseCase,
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

    fun fetchOAuthCheck() =
        execute(
            job = { fetchGithubOAuthCheckUseCase.execute(Unit) },
            onSuccess = {
                val success = it.is_connected
                Log.d("TAG", "fetchOAuthCheck: $success")
                emitEvent(
                    if (success) {
                        Event.OAuthConnected
                    } else {
                        Event.OAuthNotConnected
                    },
                )
            },
            onFailure = { emitEvent(Event.Failure) }
        )

    sealed class Event {

        object LogoutSuccess : Event()
        data class Success(val data: ProfileEntity) : Event()
        object Failure : Event()

        object OAuthConnected : Event()

        object OAuthNotConnected : Event()
        data class UploadFileSuccess(val data: List<String>) : Event()
        object UploadFileFailure : Event()

        object ImageChangeSuccess : Event()
        object ImageChangeFailure : Event()
    }
}