package com.xquare.domain.entity.timetables

import java.time.LocalTime
import java.util.UUID

data class DayTimetableEntity(
    val date: String,
    val times: List<DayTimetableDataEntity>
) {
    data class DayTimetableDataEntity(
        val id: UUID,
        val period: Int,
        val begin_time: LocalTime,
        val end_time: LocalTime,
        val type: String,
    )
}
