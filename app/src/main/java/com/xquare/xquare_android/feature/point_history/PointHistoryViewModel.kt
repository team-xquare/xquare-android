package com.xquare.xquare_android.feature.point_history

import com.xquare.domain.entity.point.PointHistoriesEntity
import com.xquare.domain.usecase.point.FetchBadPointHistoriesUseCase
import com.xquare.domain.usecase.point.FetchGoodPointHistoriesUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PointHistoryViewModel @Inject constructor(
    private val fetchGoodPointHistoriesUseCase: FetchGoodPointHistoriesUseCase,
    private val fetchBadPointHistoriesUseCase: FetchBadPointHistoriesUseCase,
) : BaseViewModel<PointHistoryViewModel.Event>() {

    private val _pointHistory = MutableStateFlow(
        PointHistoriesEntity(
            goodPoint = 0,
            badPoint = 0,
            pointHistories = listOf(),
        )
    )
    val pointHistory: StateFlow<PointHistoriesEntity> = _pointHistory
    fun fetchGoodPointHistories(offlineOnly: Boolean = true) {
        execute(
            job = { fetchGoodPointHistoriesUseCase.execute(offlineOnly) },
            onSuccess = { it.collect { pointHistory -> _pointHistory.tryEmit(pointHistory)} },
            onFailure = { emitEvent(Event.Failure) }
        )
    }

    fun fetchBadPointHistories(offlineOnly: Boolean = true) {
        execute(
            job = { fetchBadPointHistoriesUseCase.execute(offlineOnly) },
            onSuccess = { it.collect { pointHistory -> _pointHistory.tryEmit(pointHistory)} },
            onFailure = { emitEvent(Event.Failure) }
        )
    }

    sealed class Event {
        object Failure : Event()
    }
}