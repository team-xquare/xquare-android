package com.xquare.data.remote.request.schedules

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.schedules.WriteSchedulesEntity

data class WriteSchedulesRequest(
    @SerializedName("name") val name: String,
    @SerializedName("date") val date: String
)

fun WriteSchedulesEntity.toRequest() =
    WriteSchedulesRequest(
        name = name,
        date = date.toString()
    )
