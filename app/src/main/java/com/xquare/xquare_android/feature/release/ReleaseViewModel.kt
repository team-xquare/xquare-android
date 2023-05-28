package com.xquare.xquare_android.feature.release

import com.xquare.domain.entity.reports.ReleaseEntity
import com.xquare.domain.usecase.release.FetchReleaseUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReleaseViewModel @Inject constructor(
    private val releaseUseCase: FetchReleaseUseCase
): BaseViewModel<ReleaseViewModel.Event>() {

    fun fetchRelease() =
        execute(
            job = { releaseUseCase.execute(Unit) },
            onSuccess = { emitEvent(Event.Success(it))},
            onFailure = { emitEvent(Event.Failure) }
        )

    sealed class Event {

        data class Success(val data: ReleaseEntity): Event()

        object Failure : Event()

    }
}