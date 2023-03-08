package com.xquare.xquare_android.feature.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.Body3
import com.semicolon.design.Subtitle4
import com.semicolon.design.button.ToggleButton
import com.semicolon.design.color.primary.dark.dark300
import com.semicolon.design.color.primary.gray.gray100
import com.semicolon.design.color.primary.gray.gray600
import com.semicolon.design.color.primary.gray.gray700
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.purple.purple400
import com.semicolon.design.color.primary.white.white
import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.timetables.TimetableEntity
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast
import org.threeten.bp.LocalDate

@Composable
fun ScheduleScreen(navController: NavController) {
    val context = LocalContext.current
    val timetableViewModel: TimetableViewModel = hiltViewModel()
    val scheduleViewModel: ScheduleViewModel = hiltViewModel()

    var pageNum by remember { mutableStateOf(0) }
    var timetableEntity: TimetableEntity? by remember { mutableStateOf(null) }

    var selectedMonth by remember { mutableStateOf(LocalDate.now().monthValue) }
    var schedulesEntity: SchedulesEntity? by remember { mutableStateOf(null) }
    var isLoadingSchedule = remember { false }
    var loadingMonth = LocalDate.now().monthValue

    LaunchedEffect(Unit) {
        timetableViewModel.fetchWeekTimetables()
        timetableViewModel.eventFlow.collect {
            when (it) {
                is TimetableViewModel.Event.Success -> {
                    timetableEntity = it.data
                    scheduleViewModel.fetchSchedules(loadingMonth)
                }
                is TimetableViewModel.Event.Failure -> {
                    makeToast(context, "시간표를 불러오지 못했습니다")
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        scheduleViewModel.schedulesList.collect {
            isLoadingSchedule = false
            selectedMonth = loadingMonth
            schedulesEntity = it
        }
    }
    Scaffold(
        modifier = Modifier
            .background(white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp
            ),
        topBar = {
            Column {
                Spacer(Modifier.size(12.dp))
                ToggleButton(items = arrayOf("시간표", "학사일정")) {
                    pageNum = it
                }
            }
        }
    ) {
        if (pageNum == 0) Timetable(timetableEntity)
        else Schedule(
            month = selectedMonth,
            schedules = schedulesEntity,
            onNextMonthClick = {
                if (!isLoadingSchedule) {
                    loadingMonth = it
                    scheduleViewModel.fetchSchedules(loadingMonth)
                    isLoadingSchedule = true
                }
            },
            onPrevMonthClick = {
                if (!isLoadingSchedule) {
                    loadingMonth = it
                    scheduleViewModel.fetchSchedules(loadingMonth)
                    isLoadingSchedule = true
                }
            }
        )
    }
}

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Timetable(
    timetableEntity: TimetableEntity?,
) {
    if (timetableEntity == null) return
    val pagerState = rememberPagerState()
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) {
                    if (it == pagerState.currentPage) {
                        Spacer(
                            modifier = Modifier
                                .size(12.dp)
                                .background(dark300, RoundedCornerShape(6.dp))
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(dark300, RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(white, RoundedCornerShape(5.dp))
                            )
                        }
                    }
                    if (it < pagerState.pageCount - 1) Spacer(Modifier.size(12.dp))
                }
            }
        }
    ) {
        HorizontalPager(
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            count = timetableEntity.week_timetable.size,
            state = pagerState
        ) {
            TimetablePage(timetableEntity.week_timetable[it])
        }
    }
}

@Composable
private fun TimetablePage(
    weekTimetableEntity: TimetableEntity.WeekTimetableEntity,
) {
    Scaffold(
        topBar = {
            Box(modifier = Modifier.padding(start = 24.dp, top = 16.dp, bottom = 8.dp)) {
                val localDate = LocalDate.parse(weekTimetableEntity.date)
                val weekday = getWeekdayStringByInt(weekTimetableEntity.week_day)
                Body1(text = "${localDate.monthValue}월 ${localDate.dayOfMonth}일 ($weekday)")
            }
        }
    ) {
        LazyColumn {
            items(weekTimetableEntity.day_timetable.size) {
                TimetableItem(weekTimetableEntity.day_timetable[it])
            }
        }
    }
}

@Composable
private fun TimetableItem(
    dayTimetableEntity: TimetableEntity.WeekTimetableEntity.DayTimetableEntity,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .padding(start = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Body1(
            text = "${dayTimetableEntity.period}교시",
            color = gray900,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.size(21.dp))
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(gray100, RoundedCornerShape(20.dp))
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp)),
                painter = rememberAsyncImagePainter(model = dayTimetableEntity.subject_image),
                contentDescription = null
            )
        }
        Spacer(Modifier.size(12.dp))
        Column {
            Body1(
                text = dayTimetableEntity.subject_name,
                color = gray900,
                fontWeight = FontWeight.Medium
            )
            Body3(
                text = "${dayTimetableEntity.begin_time} ~ ${dayTimetableEntity.end_time}",
                color = gray600
            )
        }
    }
}

@Composable
private fun Schedule(
    month: Int,
    schedules: SchedulesEntity?,
    onPrevMonthClick: (curMonth: Int) -> Unit,
    onNextMonthClick: (curMonth: Int) -> Unit,
) {
    if (schedules == null) return
    val lazyState = rememberLazyListState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 20.dp)
                ) {
                    ScheduleCalendar(month = month,
                        schedules = schedules,
                        listState = lazyState,
                        onPrevMonthClick = { onPrevMonthClick(it) },
                        onNextMonthClick = { onNextMonthClick(it) }
                    )
                }
            }
        ) {
            LazyColumn(
                state = lazyState,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(schedules.schedules.size) {
                    ScheduleItem(schedules.schedules[it])
                }
            }
        }
    }
}

@Composable
fun ScheduleItem(
    schedulesDataEntity: SchedulesEntity.SchedulesDataEntity,
) {
    val date = LocalDate.parse(schedulesDataEntity.date)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(
                modifier = Modifier
                    .width(4.dp)
                    .height(64.dp)
                    .background(purple400)
            )
            Spacer(Modifier.size(21.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Subtitle4(
                    text = date.dayOfMonth.toString(),
                    color = gray900,
                    fontWeight = FontWeight.Medium
                )
                Body2(
                    text = getWeekdayStringByInt(date.dayOfWeek.value),
                    color = gray700,
                )
            }
            Spacer(Modifier.size(21.dp))
            Column {
                Body1(
                    text = schedulesDataEntity.name,
                    color = gray900,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.size(4.dp))
                Body2(
                    text = "하루종일",
                    color = gray700,
                )

            }
        }
    }
}

private fun getWeekdayStringByInt(int: Int) =
    when (int) {
        1 -> "월"
        2 -> "화"
        3 -> "수"
        4 -> "목"
        5 -> "금"
        6 -> "토"
        7 -> "일"
        else -> throw IllegalArgumentException()
    }