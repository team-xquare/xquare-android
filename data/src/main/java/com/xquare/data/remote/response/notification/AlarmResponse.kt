package com.xquare.data.remote.response.notification

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.notification.AlarmEntity
import java.time.LocalDateTime

data class AlarmResponse(
    @SerializedName("notifications") val notifications: List<AlarmDataResponse>
) {
    data class AlarmDataResponse(
        @SerializedName("id") val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("content") val content: String,
        @SerializedName("dateTime") val dateTime: LocalDateTime,
        @SerializedName("is_read") val is_read: Boolean,
        @SerializedName("user_id") val user_id: String,
        @SerializedName("name") val name: String,
        @SerializedName("destination") val destination: String,
    )
}

fun AlarmResponse.toEntity() =
    AlarmEntity(
        notifications = notifications.map { it.toEntity() }
    )

fun AlarmResponse.AlarmDataResponse.toEntity() =
    AlarmEntity.AlarmDataEntity(
        id = id,
        title = title,
        content = content,
        dateTime = dateTime,
        is_read = is_read,
        user_id = user_id,
        name = name,
        destination = destination,
    )
