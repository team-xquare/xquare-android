package com.xquare.xquare_android.feature.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.semicolon.design.Body3
import com.semicolon.design.button.ToggleButton
import com.semicolon.design.color.primary.dark.dark300
import com.semicolon.design.color.primary.gray.gray100
import com.semicolon.design.color.primary.gray.gray600
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.white.white
import com.xquare.domain.entity.timetables.TimetableEntity
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast
import org.threeten.bp.LocalDate

@Composable
fun ScheduleScreen(navController: NavController) {
    val context = LocalContext.current
    val timetableViewModel: TimetableViewModel = hiltViewModel()

    var pageNum by remember { mutableStateOf(0) }
    var timetableEntity: TimetableEntity? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        timetableViewModel.fetchWeekTimetables()
        timetableViewModel.eventFlow.collect {
            when (it) {
                is TimetableViewModel.Event.Success -> {
                    timetableEntity = it.data
                }
                is TimetableViewModel.Event.Failure -> {
                    makeToast(context, "시간표를 불러오지 못했습니다")
                }
            }
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
        if (pageNum == 0) Timetable(timetableEntity) else Schedule()
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
private fun Schedule() {

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