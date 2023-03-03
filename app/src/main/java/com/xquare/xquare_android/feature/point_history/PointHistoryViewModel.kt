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

    fun fetchGoodPointHistories() =
        execute(
            job = { fetchGoodPointHistoriesUseCase.execute(Unit) },
            onSuccess = { it.collect { pointHistory -> emitEvent(Event.Success(pointHistory)) } },
            onFailure = { emitEvent(Event.Failure) }
        )

    fun fetchBadPointHistories() =
        execute(
            job = { fetchBadPointHistoriesUseCase.execute(Unit) },
            onSuccess = { it.collect { pointHistory -> emitEvent(Event.Success(pointHistory)) } },
            onFailure = { emitEvent(Event.Failure) }
        )

    sealed class Event {
        data class Success(val data: PointHistoriesEntity) : Event()
        object Failure : Event()
    }
}