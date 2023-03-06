package com.xquare.xquare_android.feature.schedule

import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.xquare.domain.entity.schedules.WriteSchedulesEntity
import com.xquare.xquare_android.util.makeToast
import kotlinx.coroutines.flow.collect
import java.time.LocalDate
import java.time.LocalDateTime

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

    val scheduleViewModel: ScheduleViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        scheduleViewModel.eventFlow.collect {
            when (it) {
                is ScheduleViewModel.Event.CreateSuccess -> {
                    Log.d("TAG", "CreateSuccess")
                }
                is ScheduleViewModel.Event.Failure -> {
                    makeToast(context, it.message)
                }
            }
        }
    }

    //일정 불러오기
    /*val scheduleList = scheduleViewModel.schedulesList.collectAsState().value
    LaunchedEffect(Unit) {
        scheduleViewModel.fetchSchedules(1)
    }
    Log.d("TAG", "scheduleList: $scheduleList") */

    //일정 생성
   /*LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            scheduleViewModel.createSchedules(
                WriteSchedulesEntity("일정 생성", LocalDate.now()))
       }
   }*/
    
}
