package com.xquare.xquare_android.feature.schedule

import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.schedules.CreateSchedulesEntity
import com.xquare.domain.entity.schedules.FixSchedulesEntity
import com.xquare.domain.usecase.schedules.CreateSchedulesUseCase
import com.xquare.domain.usecase.schedules.FetchSchedulesUseCase
import com.xquare.domain.usecase.schedules.FixSchedulesUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val fetchSchedulesUseCase: FetchSchedulesUseCase,
    private val createSchedulesUseCase: CreateSchedulesUseCase,
    private val fixSchedulesUseCase: FixSchedulesUseCase,
): BaseViewModel<ScheduleViewModel.Event>() {

    private val _schedulesList = MutableStateFlow(SchedulesEntity(listOf()))
    val schedulesList: StateFlow<SchedulesEntity> = _schedulesList

    fun fetchSchedules(month: Int) {
        execute(
            job = { fetchSchedulesUseCase.execute(month) },
            onSuccess = { _schedulesList.tryEmit(it) },
            onFailure = { emitEvent(Event.Failure("일정을 불러오는데 실패하였습니다.")) }
        )
    }

    fun createSchedules(data: CreateSchedulesEntity) {
        execute(
            job = { createSchedulesUseCase.execute(data) },
            onSuccess = { emitEvent(Event.CreateSuccess) },
            onFailure = { emitEvent(Event.Failure("일정을 생성하는데 실패하였습니다")) }
        )
    }

    fun fixSchedules(data: FixSchedulesEntity) {
        execute(
            job = { fixSchedulesUseCase.execute(data) },
            onSuccess = { emitEvent(Event.FixSuccess) },
            onFailure = { emitEvent(Event.Failure("일정을 수정하는데 실패했습니다")) }
        )
    }
    sealed class Event {
        object CreateSuccess : Event()
        object FixSuccess : Event()
        data class Failure(val message: String) : Event()
    }
}