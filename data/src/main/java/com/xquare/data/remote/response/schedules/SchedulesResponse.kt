package com.xquare.data.remote.response.schedules

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.schedules.SchedulesEntity

data class SchedulesResponse(
    @SerializedName("schedules") val schedules: List<SchedulesDataResponse>
) {
    data class SchedulesDataResponse(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("date") val date: String
    )
}

fun SchedulesResponse.toEntity() = SchedulesEntity(
    schedules = schedules.map { it.toEntity() }
)

fun SchedulesResponse.SchedulesDataResponse.toEntity() =
    SchedulesEntity.SchedulesDataEntity(
        id = id,
        name = name,
        date = date
    )
