package com.xquare.xquare_android.feature.schedule

import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.schedules.CreateSchedulesEntity
import com.xquare.domain.entity.schedules.FixSchedulesEntity
import com.xquare.domain.usecase.schedules.CreateSchedulesUseCase
import com.xquare.domain.usecase.schedules.DeleteSchedulesUseCase
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
    private val deleteSchedulesUseCase: DeleteSchedulesUseCase,
): BaseViewModel<ScheduleViewModel.Event>() {

    private val _schedulesList = MutableStateFlow(SchedulesEntity(listOf()))
    val schedulesList: StateFlow<SchedulesEntity> = _schedulesList

    fun fetchSchedules(month: Int) {
        execute(
            job = { fetchSchedulesUseCase.execute(month) },
            onSuccess = { emitEvent(Event.Success(it)) },
            onFailure = {  }
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

    fun deleteSchedules(id: String) {
        execute(
            job = { deleteSchedulesUseCase.execute(id) },
            onSuccess = { emitEvent(Event.DeleteSuccess) },
            onFailure = { emitEvent(Event.Failure("일정을 삭제하지 못했습니다")) }
        )
    }
    sealed class Event {
        data class Success(val data: SchedulesEntity): Event()
        object CreateSuccess : Event()
        object FixSuccess : Event()
        object DeleteSuccess : Event()
        data class Failure(val message: String) : Event()
    }
}