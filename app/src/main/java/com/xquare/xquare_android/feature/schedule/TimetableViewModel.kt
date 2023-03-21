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

    private val _timetable = MutableStateFlow(
        TimetableEntity(
            emptyList()
        ))
    val timetable: MutableStateFlow<TimetableEntity> = _timetable
    fun fetchWeekTimetables() {
        execute(
            job = { fetchWeekTimetablesUseCase.execute(Unit) },
            onSuccess = { it.collect { timetable -> _timetable.tryEmit(timetable) } },
            onFailure = {  }
        )

        Log.d("TEST", _timetable.toString())
    }

    sealed class Event {}
}