package com.xquare.data.remote.request.notification

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.notification.ActivateAlarmEntity

data class ActivateAlarmRequest(
    @SerializedName("is-activated") val isActivated: Boolean,
    @SerializedName("topic") val topic: String,
)

fun ActivateAlarmEntity.toRequest() =
    ActivateAlarmRequest(
        isActivated = isActivated,
        topic = topic
    )