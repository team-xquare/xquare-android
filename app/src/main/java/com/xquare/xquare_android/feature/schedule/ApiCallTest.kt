package com.xquare.xquare_android.feature.schedule

import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.xquare.domain.entity.schedules.CreateSchedulesEntity
import com.xquare.domain.entity.schedules.FixSchedulesEntity
import com.xquare.xquare_android.util.makeToast
import java.time.LocalDate

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
                is ScheduleViewModel.Event.FixSuccess -> {
                    Log.d("TAG", "FixSuccess")
                }
                is ScheduleViewModel.Event.DeleteSuccess -> {
                    Log.d("TAG", "DeleteSuccess")
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
        scheduleViewModel.fetchSchedules(3)
    }
    Log.d("TAG", "scheduleList: $scheduleList")*/

    //일정 생성
   /*LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            scheduleViewModel.createSchedules(
                CreateSchedulesEntity("일정 생성", LocalDate.now())
            )
       }
   }*/

    /*LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            scheduleViewModel.fixSchedules(
                FixSchedulesEntity("54f51e98-6d78-4487-bb4b-cc233d765321","씨발 세상", LocalDate.now())
            )
        }
    }*/
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            scheduleViewModel.deleteSchedules("04fe75ce-fc62-4327-86a0-b6d1744966b7")
        }
    }
}
