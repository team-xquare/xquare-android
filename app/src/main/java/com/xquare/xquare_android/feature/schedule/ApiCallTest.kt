package com.xquare.xquare_android.feature.schedule

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.xquare.xquare_android.util.makeToast
import kotlinx.coroutines.flow.collect

@Composable
fun Timetable() {
    val context = LocalContext.current
    // 시간표 불러오기
    /*val timetableViewModel: TimetableViewModel = hiltViewModel()
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
    }*/

    //일정 불러오기
    /*val scheduleViewModel: ScheduleViewModel = hiltViewModel()
    val scheduleList = scheduleViewModel.schedulesList.collectAsState().value
    LaunchedEffect(Unit) {
        scheduleViewModel.fetchSchedules(1)
        scheduleViewModel.eventFlow.collect {
            when (it) {
                is ScheduleViewModel.Event.Failure -> {
                    makeToast(context, it.message)
                }
            }
        }
    }
    Log.d("TAG", "scheduleList: $scheduleList") */
}
