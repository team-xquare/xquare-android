package com.xquare.xquare_android.feature.bug

import com.xquare.domain.entity.bug.BugEntity
import com.xquare.domain.usecase.bug.UploadBugUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BugViewModel @Inject constructor(
    private val bugUseCase: UploadBugUseCase
): BaseViewModel<BugViewModel.Event>(){

    fun uploadBug(bugEntity: BugEntity) = execute(
        job = { bugUseCase.execute(bugEntity) },
        onSuccess = { emitEvent(Event.Success)},
        onFailure = { emitEvent(Event.Failure) }
    )

    sealed class Event {
        object Success : Event()
        object Failure : Event()
    }
}