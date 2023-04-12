package com.xquare.xquare_android.feature.point_history

import com.xquare.domain.entity.point.PointHistoriesEntity
import com.xquare.domain.usecase.point.FetchBadPointHistoriesUseCase
import com.xquare.domain.usecase.point.FetchGoodPointHistoriesUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PointHistoryViewModel @Inject constructor(
    private val fetchGoodPointHistoriesUseCase: FetchGoodPointHistoriesUseCase,
    private val fetchBadPointHistoriesUseCase: FetchBadPointHistoriesUseCase,
) : BaseViewModel<PointHistoryViewModel.Event>() {

    fun fetchGoodPointHistories(offlineOnly: Boolean = true) {
        execute(
            job = { fetchGoodPointHistoriesUseCase.execute(offlineOnly) },
            onSuccess = { it.collect { pointHistory -> emitEvent(Event.Success(pointHistory)) } },
            onFailure = { emitEvent(Event.Failure) }
        )
    }

    fun fetchBadPointHistories(offlineOnly: Boolean = true) {
        execute(
            job = { fetchBadPointHistoriesUseCase.execute(offlineOnly) },
            onSuccess = { it.collect { pointHistory -> emitEvent(Event.Success(pointHistory)) } },
            onFailure = { emitEvent(Event.Failure) }
        )
    }

    sealed class Event {
        data class Success(val data: PointHistoriesEntity) : Event()
        object Failure : Event()
    }
}