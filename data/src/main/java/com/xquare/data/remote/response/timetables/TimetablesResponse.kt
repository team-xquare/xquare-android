package com.xquare.data.remote.response.timetables

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.timetables.TimetableEntity

data class TimetablesResponse(
    @SerializedName("week_timetable") val week_timetable: List<WeekTimeTableResponse>
) {
    data class WeekTimeTableResponse(
        @SerializedName("week_day") val week_day: Int,
        @SerializedName("date") val date: String,
        @SerializedName("day_timetable") val day_timetable: List<DayTimeTableResponse>
    ) {
        data class DayTimeTableResponse(
            @SerializedName("period") val period: Int,
            @SerializedName("begin_time") val begin_time: String,
            @SerializedName("end_time") val end_time: String,
            @SerializedName("subject_name") val subject_name: String,
            @SerializedName("subject_image") val subject_image: String,
        )
    }
}

fun TimetablesResponse.toEntity() =
    TimetableEntity(
        week_timetable = week_timetable.map { it.toEntity() }
    )

fun TimetablesResponse.WeekTimeTableResponse.toEntity() =
    TimetableEntity.WeekTimetableEntity(
        week_day = week_day,
        date = date,
        day_timetable = day_timetable.map { it.toEntity() }
    )

fun TimetablesResponse.WeekTimeTableResponse.DayTimeTableResponse.toEntity() =
    TimetableEntity.WeekTimetableEntity.DayTimetableEntity(
        period = period,
        begin_time = begin_time.substring(0..4),
        end_time = end_time.substring(0..4),
        subject_name = subject_name,
        subject_image = subject_image,
    )