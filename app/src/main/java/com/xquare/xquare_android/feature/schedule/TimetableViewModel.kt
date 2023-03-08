package com.xquare.xquare_android.feature.schedule

import com.xquare.domain.entity.timetables.TimetableEntity
import com.xquare.domain.usecase.timetables.FetchWeekTimetablesUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val fetchWeekTimetablesUseCase: FetchWeekTimetablesUseCase,
): BaseViewModel<TimetableViewModel.Event>() {
    fun fetchWeekTimetables() {
        execute(
            job = { fetchWeekTimetablesUseCase.execute(Unit) },
            onSuccess = { emitEvent(Event.Success(it)) },
            onFailure = { emitEvent(Event.Failure) }
        )
    }

    sealed class Event {
        data class Success(val data: TimetableEntity) : Event()
        object Failure : Event()
    }
}