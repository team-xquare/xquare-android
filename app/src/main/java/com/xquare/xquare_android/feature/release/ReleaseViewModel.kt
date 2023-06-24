package com.xquare.xquare_android.feature.release

import com.xquare.domain.entity.reports.ReleaseEntity
import com.xquare.domain.usecase.release.FetchReleaseUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ReleaseViewModel @Inject constructor(
    private val releaseUseCase: FetchReleaseUseCase
): BaseViewModel<ReleaseViewModel.Event>() {

    private val _release = MutableStateFlow(ReleaseEntity(listOf()))
    val release: StateFlow<ReleaseEntity> = _release

    fun fetchRelease() =
        execute(
            job = { releaseUseCase.execute(Unit) },
            onSuccess = { it.collect{ release -> _release.tryEmit(release)} },
            onFailure = { emitEvent(Event.Failure) }
        )

    sealed class Event {

        data class Success(val data: ReleaseEntity): Event()

        object Failure : Event()

    }
}