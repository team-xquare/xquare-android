package com.xquare.xquare_android.feature.schedule

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.semicolon.design.Body2
import com.semicolon.design.color.primary.gray.gray500
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.purple.purple100
import com.semicolon.design.color.primary.purple.purple400
import com.semicolon.design.color.primary.white.white
import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.xquare_android.R
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.chrono.IsoChronology

@SuppressLint("UnrememberedMutableState")
@Composable
fun ScheduleCalendar(
    month: Int,
    schedules: SchedulesEntity,
    plusHeight: () -> Int,
    onPrevMonthClick: (prevMonth: Int) -> Unit,
    onNextMonthClick: (nextMonth: Int) -> Unit,
    onCollapsedAll: () -> Unit,
    onExpandedAll: () -> Unit,
) {

    val localDensity = LocalDensity.current
    val now = LocalDate.now()
    val year = if (now.monthValue in 3..12) {
        if (month in 3..12) now.year else now.year + 1
    } else {
        if (month in 1..2) now.year else now.year - 1
    }
    var prev by remember { mutableStateOf(month) }

    val calendarDateList by mutableStateOf(getCalendarDateList(year, month))
    val slicedCalendarDateList = mutableListOf<List<LocalDate>>()
    for (i in 0 until calendarDateList.size / 7) {
        slicedCalendarDateList.add(calendarDateList.subList(i * 7, i * 7 + 7))
    }

    val minHeight = with(localDensity) { 36.dp.toPx() }
    val maxHeight = with(localDensity) {
        (36.dp.toPx() * slicedCalendarDateList.size) + (8.dp.toPx() * (slicedCalendarDateList.size - 1))
    }
    var calendarHeight by remember { mutableStateOf(maxHeight) }
    var isCompact by remember { mutableStateOf(false) }
    var compactListIndex by remember { mutableStateOf(-1) }
    if (!isCompact) {
        calendarHeight = maxHeight
        compactListIndex = -1
        prev = month
    } else {
        if (prev < month) compactListIndex = 0
        else if (prev > month) compactListIndex = slicedCalendarDateList.lastIndex
        prev = month
    }

    calendarHeight = (calendarHeight + plusHeight.invoke()).coerceIn(minHeight, maxHeight)
    when (calendarHeight) {
        minHeight -> onCollapsedAll()
        maxHeight -> onExpandedAll()
    }
    isCompact = calendarHeight != maxHeight

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .height(20.dp)
                        .padding(horizontal = 3.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                        ) {
                            if (isCompact && compactListIndex > 0) {
                                compactListIndex -= 1
                            } else if (month != 3) {
                                val prevMonth = if (month == 1) 12 else month - 1
                                onPrevMonthClick(prevMonth)
                            }
                        },
                    painter = painterResource(R.drawable.ic_arrow_prev),
                    tint = gray500,
                    contentDescription = null
                )
                Spacer(Modifier.size(22.dp))
                Body2(text = "${year}년 ${month}월")
                Spacer(Modifier.size(22.dp))
                Icon(
                    modifier = Modifier
                        .height(20.dp)
                        .padding(horizontal = 3.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                        ) {
                            if (isCompact && compactListIndex < slicedCalendarDateList.lastIndex) {
                                compactListIndex += 1
                            } else if (month != 2) {
                                val nextMonth = if (month == 12) 1 else month + 1
                                onNextMonthClick(nextMonth)
                            }
                        },
                    painter = painterResource(R.drawable.ic_arrow_next),
                    tint = gray500,
                    contentDescription = null
                )
            }
            Spacer(Modifier.size(8.dp))
            if (!isCompact) {
                slicedCalendarDateList.forEachIndexed { idx, list ->
                    Row {
                        list.forEach {
                            val dateSchedules = schedules.schedules.firstOrNull { data ->
                                data.date == it.toString()
                            }
                            ScheduleCalendarItem(
                                day = it.dayOfMonth,
                                isToday = it == now,
                                haveSchedule = dateSchedules != null,
                                isThisMonth = it.monthValue == month
                            )
                        }
                    }
                    if (idx != slicedCalendarDateList.lastIndex) Spacer(Modifier.size(8.dp))
                }
            } else if (compactListIndex == -1 && now.monthValue == month) {
                compactListIndex = slicedCalendarDateList.indexOfFirst { it.contains(now) }
            } else if (compactListIndex == -1) {
                compactListIndex = 0
            } else {
                val height = with(localDensity) { calendarHeight.toDp() }
                Column(
                    modifier = Modifier
                        .height(height),
                    verticalArrangement = Arrangement.Center
                ) {
                    slicedCalendarDateList.getOrNull(compactListIndex)?.let { list ->
                        Row {
                            list.forEach {
                                val dateSchedules = schedules.schedules.firstOrNull { data ->
                                    data.date == it.toString()
                                }
                                ScheduleCalendarItem(
                                    day = it.dayOfMonth,
                                    isToday = it == now,
                                    haveSchedule = dateSchedules != null,
                                    isThisMonth = it.monthValue == month
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.ScheduleCalendarItem(
    day: Int,
    isToday: Boolean,
    haveSchedule: Boolean,
    isThisMonth: Boolean,
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .height(36.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            isToday -> {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(purple400, RoundedCornerShape(18.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Body2(
                        text = day.toString(),
                        fontWeight = FontWeight.Medium,
                        color = white
                    )
                }
            }
            haveSchedule -> {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .border(1.8.dp, purple100, RoundedCornerShape(18.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Body2(
                        text = day.toString(),
                        fontWeight = FontWeight.Medium,
                        color = gray900
                    )
                }
            }
            else -> {
                Body2(
                    text = day.toString(),
                    fontWeight = FontWeight.Medium,
                    color = if (isThisMonth) gray900 else gray500
                )
            }
        }
    }
}

private fun getCalendarDateList(year: Int, month: Int): List<LocalDate> {
    val result = mutableListOf<LocalDate>()
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    with(firstDayOfMonth) {
        repeat(dayOfWeek.value) {
            result.add(minusDays((dayOfWeek.value - it).toLong()))
        }
    }
    for (i in 1..Month.of(month).length(IsoChronology.INSTANCE.isLeapYear(year.toLong()))) {
        result.add(LocalDate.of(year, month, i))
    }
    if (result.size % 7 != 0) {
        repeat(7 - result.size % 7) {
            result.add(result.last().plusDays(1L))
        }
    }
    return result
}