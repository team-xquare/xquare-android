package com.xquare.xquare_android.feature.schedule

import android.util.Log
import androidx.lifecycle.LiveData
import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.usecase.schedules.FetchSchedulesUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val fetchSchedulesUseCase: FetchSchedulesUseCase,
): BaseViewModel<ScheduleViewModel.Event>() {

    private val _schedulesList = MutableStateFlow(SchedulesEntity(listOf()))
    val schedulesList: StateFlow<SchedulesEntity> = _schedulesList

    fun fetchSchedules(month: Int) {
        execute(
            job = { fetchSchedulesUseCase.execute(month) },
            onSuccess = { _schedulesList.tryEmit(it) },
            onFailure = {
                Log.d("TAG", "fetchSchedules: $it")
                emitEvent(Event.Failure("일정을 불러오는데 실패하였습니다."))
            }
        )
    }
    sealed class Event {
        data class Failure(val message: String) : Event()
    }
}