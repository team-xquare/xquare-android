package com.xquare.xquare_android.feature.schedule

import android.util.Log
import com.xquare.domain.entity.timetables.TimetableEntity
import com.xquare.domain.usecase.timetables.FetchWeekTimetablesUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val fetchWeekTimetablesUseCase: FetchWeekTimetablesUseCase,
): BaseViewModel<TimetableViewModel.Event>() {
    init {
        fetchWeekTimetables()
    }

    private val _timetable = MutableStateFlow(
        TimetableEntity(
            emptyList()
        ))
    val timetable: MutableStateFlow<TimetableEntity> = _timetable
    private fun fetchWeekTimetables() {
        execute(
            job = { fetchWeekTimetablesUseCase.execute(Unit) },
            onSuccess = { it.collect { timetable -> _timetable.tryEmit(timetable) } },
            onFailure = {  }
        )
    }

    sealed class Event {}
}