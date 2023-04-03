package com.xquare.data.remote.response.pick

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.pick.ClassPositionEntity

data class ClassPositionResponse(
    @SerializedName("name") val name: String,
    @SerializedName("location_classroom") val location_classroom: String,
)

fun ClassPositionResponse.toEntity() =
    ClassPositionEntity(
        name = name,
        location_classroom = location_classroom,
    )
