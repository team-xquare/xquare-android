package com.xquare.xquare_android.feature.bug

import com.xquare.domain.entity.bug.BugEntity
import com.xquare.domain.usecase.attachment.UploadFileUseCase
import com.xquare.domain.usecase.bug.UploadBugUseCase
import com.xquare.xquare_android.base.BaseViewModel
import com.xquare.xquare_android.feature.profile.ProfileViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class BugViewModel @Inject constructor(
    private val bugUseCase: UploadBugUseCase,
    private val uploadFileUseCase: UploadFileUseCase
): BaseViewModel<BugViewModel.Event>(){

    fun uploadBug(bugEntity: BugEntity) = execute(
        job = { bugUseCase.execute(bugEntity) },
        onSuccess = { emitEvent(Event.Success)},
        onFailure = { emitEvent(Event.Failure) }
    )

    fun uploadFile(file: File) = execute(
        job = {uploadFileUseCase.execute(file)},
        onSuccess = {emitEvent(Event.UploadFileSuccess(it.file_url))},
        onFailure = { emitEvent(Event.Failure)}
    )

    sealed class Event {
        object Success : Event()
        data class UploadFileSuccess(val data: List<String>) : Event()
        object Failure : Event()
    }
}