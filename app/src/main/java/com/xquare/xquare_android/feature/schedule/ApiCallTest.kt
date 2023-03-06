package com.xquare.xquare_android.feature.schedule

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Timetable() {
    val timetableViewModel: TimetableViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        timetableViewModel.fetchWeekTimetables()
        timetableViewModel.eventFlow.collect {
            when (it) {
                is TimetableViewModel.Event.Success -> {
                    Log.d("TAG", "TimetableViewModel: "+it.data)
                }
                is TimetableViewModel.Event.Failure -> {
                    Log.d("TAG", "TimetableViewModel: Fail")
                }
            }
        }
    }
}
