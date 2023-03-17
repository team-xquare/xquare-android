package com.xquare.data.remote.response.timetables

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.timetables.DayTimetableEntity
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

data class DayTimetablesResponse(
    @SerializedName("date") val date: String,
    @SerializedName("times") val times: List<DayTimetablesDataResponse>
) {
    data class DayTimetablesDataResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("period") val period: Int,
        @SerializedName("begin_time") val begin_time: String,
        @SerializedName("end_time") val end_time: String,
        @SerializedName("type") val type: String,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun DayTimetablesResponse.toEntity() =
    DayTimetableEntity(
        date = date,
        times = times.map { it.toEntity() },
    )

@RequiresApi(Build.VERSION_CODES.O)
fun DayTimetablesResponse.DayTimetablesDataResponse.toEntity(): DayTimetableEntity.DayTimetableDataEntity {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    Log.d("TAG", "begin_time: $begin_time")
    Log.d("TAG", "end_time: $end_time")
    Log.d("TAG", "begin_time: "+ LocalTime.parse(begin_time, formatter))
    Log.d("TAG", "end_time: "+LocalTime.parse(end_time, formatter))
    return DayTimetableEntity.DayTimetableDataEntity(
        id = id,
        period = period,
        begin_time = LocalTime.parse(begin_time, formatter),
        end_time = LocalTime.parse(end_time, formatter),
        type = type,
    )
}

