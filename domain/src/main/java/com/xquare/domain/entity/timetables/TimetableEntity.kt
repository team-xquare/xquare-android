package com.xquare.domain.entity.timetables

data class TimetableEntity(
    val week_timetable: List<WeekTimetableEntity>
) {
    data class WeekTimetableEntity(
        val week_day: Int,
        val date: String,
        val day_timetable: List<DayTimetableEntity>
    ) {
        data class DayTimetableEntity(
            val period: Int,
            val begin_time: String,
            val end_time: String,
            val subject_name: String,
            val subject_image: String,
        )
    }
}