package com.xquare.data.remote.request.schedules

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.schedules.CreateSchedulesEntity
import com.xquare.domain.entity.schedules.FixSchedulesEntity

data class WriteSchedulesRequest(
    @SerializedName("name") val name: String,
    @SerializedName("date") val date: String
)

fun CreateSchedulesEntity.toRequest() =
    WriteSchedulesRequest(
        name = name,
        date = date.toString()
    )

fun FixSchedulesEntity.toRequest() =
    WriteSchedulesRequest(
        name = name,
        date = date.toString()
    )
