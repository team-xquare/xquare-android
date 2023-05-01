package com.xquare.data.remote.response.picnic

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.picnic.PicnicTimeEntity

data class PicnicTimeResponse(
    @SerializedName("picnic_start_time") val picnicStartTime: String,
    @SerializedName("picnic_end_time") val picnicEndTime: String
)

fun PicnicTimeResponse.toEntity() =
    PicnicTimeEntity(
        picnicStartTime = picnicStartTime,
        picnicEndTime = picnicEndTime
    )