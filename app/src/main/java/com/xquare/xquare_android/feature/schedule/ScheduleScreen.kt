package com.xquare.xquare_android.feature.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.semicolon.design.color.primary.gray.*
import com.semicolon.design.color.primary.purple.purple400
import com.semicolon.design.color.primary.white.white
import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.timetables.TimetableEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.ActionSheet
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScheduleScreen(navController: NavController) {
    val context = LocalContext.current
    val timetableViewModel: TimetableViewModel = hiltViewModel()
    val scheduleViewModel: ScheduleViewModel = hiltViewModel()

    val actionSheetScope = rememberCoroutineScope()
    val actionSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var selectedItem: SchedulesEntity.SchedulesDataEntity? = remember { null }

    var pageNum by remember { mutableStateOf(0) }
    var timetableEntity: TimetableEntity? by remember { mutableStateOf(null) }

    var scheduleData: Pair<Int, SchedulesEntity>? by remember { mutableStateOf(null) }
    var isLoadingSchedule by remember { mutableStateOf(false) }
    var loadingMonth by remember { mutableStateOf(LocalDate.now().monthValue) }

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
    LaunchedEffect(Unit) {
        scheduleViewModel.fetchSchedules(loadingMonth)
        scheduleViewModel.eventFlow.collect {
            when (it) {
                is ScheduleViewModel.Event.Success -> {
                    isLoadingSchedule = false
                    scheduleData = Pair(loadingMonth, it.data)
                }
                is ScheduleViewModel.Event.DeleteSuccess -> {
                    isLoadingSchedule = true
                    scheduleViewModel.fetchSchedules(loadingMonth)
                    makeToast(context, "일정을 삭제하였습니다")
                }
                else -> {}
            }
        }
    }
    ActionSheet(
        state = actionSheetState,
        list = listOf("수정하기", "삭제하기"),
        onClick = { index ->
            actionSheetScope.launch {
                actionSheetState.hide()
                selectedItem?.let {
                    when (index) {
                        0 -> withContext(Dispatchers.Main) {
                            navController.navigate(AppNavigationItem.WriteSchedule.createRoute(it))
                        }
                        1 -> scheduleViewModel.deleteSchedules(it.id)
                    }
                }
            }
        }
    ) {
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
            if (pageNum == 0) Timetable(LocalDate.now().dayOfWeek.value, timetableEntity)
            else Schedule(
                data = scheduleData,
                actionSheetScope = actionSheetScope,
                actionSheetState = actionSheetState,
                onNextMonthClick = {
                    if (!isLoadingSchedule) {
                        loadingMonth = it
                        isLoadingSchedule = true
                        scheduleViewModel.fetchSchedules(loadingMonth)
                    }
                },
                onPrevMonthClick = {
                    if (!isLoadingSchedule) {
                        loadingMonth = it
                        isLoadingSchedule = true
                        scheduleViewModel.fetchSchedules(loadingMonth)
                    }
                },
                onWriteScheduleClick = {
                    navController.navigate(AppNavigationItem.WriteSchedule.createRoute(null))
                },
                onItemSelected = {
                    selectedItem = it
                }
            )
        }
    }
}

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Timetable(
    dayOfWeek: Int,
    timetableEntity: TimetableEntity?,
) {
    if (timetableEntity == null) return
    val pagerState = rememberPagerState()
    LaunchedEffect(Unit) {
        // val lastIndex = timetableEntity.week_timetable.lastIndex
        //val initPage = dayOfWeek.coerceIn(0, lastIndex)
        pagerState.scrollToPage(0)
    }
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
        ) { idx ->
            TimetablePage(timetableEntity.week_timetable[idx])
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

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Schedule(
    data: Pair<Int, SchedulesEntity>?,
    actionSheetScope: CoroutineScope,
    actionSheetState: ModalBottomSheetState,
    onPrevMonthClick: (curMonth: Int) -> Unit,
    onNextMonthClick: (curMonth: Int) -> Unit,
    onWriteScheduleClick: () -> Unit,
    onItemSelected: (SchedulesEntity.SchedulesDataEntity) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        if (data == null) return@Box
        val monthValue = data.first
        val schedulesValue = data.second
        var calendarPlusHeightPx by remember { mutableStateOf(0) }
        var isCollapsedAll = remember { false }
        var isExpandedAll = remember { true }
        val nestedScrollConnection =
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    return if (available.y < 0) {
                        isExpandedAll = false
                        calendarPlusHeightPx = available.y.toInt()
                        if (isCollapsedAll) Offset.Zero else available
                    } else {
                        isCollapsedAll = false
                        calendarPlusHeightPx = available.y.toInt()
                        if (isExpandedAll) Offset.Zero else available
                    }
                }
            }
        Scaffold(
            modifier = Modifier,
            topBar = {
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 20.dp)
                ) {
                    ScheduleCalendar(month = monthValue,
                        schedules = schedulesValue,
                        plusHeight = { calendarPlusHeightPx },
                        onPrevMonthClick = { onPrevMonthClick(it) },
                        onNextMonthClick = { onNextMonthClick(it) },
                        onCollapsedAll = {
                            isCollapsedAll = true
                        },
                        onExpandedAll = {
                            isExpandedAll = true
                        }
                    )
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier.nestedScroll(nestedScrollConnection),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(schedulesValue.schedules.size) {
                    val scheduleData = schedulesValue.schedules[it]
                    ScheduleItem(scheduleData) {
                        onItemSelected(scheduleData)
                        actionSheetScope.launch {
                            actionSheetState.show()
                        }
                    }
                }
                item {
                    Spacer(Modifier.size(88.dp))
                }
            }
        }
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .size(56.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        enabled = true
                    ) { onWriteScheduleClick() },
                shape = RoundedCornerShape(28.dp),
                color = purple400,
                elevation = 4.dp
            ) {
                Icon(
                    modifier = Modifier.padding(16.dp),
                    painter = painterResource(R.drawable.ic_write),
                    tint = white,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun ScheduleItem(
    schedulesDataEntity: SchedulesEntity.SchedulesDataEntity,
    onSeeMoreClick: () -> Unit,
) {
    val date = LocalDate.parse(schedulesDataEntity.date)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
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
        Box(Modifier
            .size(36.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                enabled = true
            ) { onSeeMoreClick() }
            .padding(6.dp)
        ) {
            Icon(
                tint = gray500,
                painter = painterResource(R.drawable.ic_see_more),
                contentDescription = null
            )
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
