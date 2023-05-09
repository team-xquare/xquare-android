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
        @SerializedName("sent_at") val send_at: String,
        @SerializedName("is_read") val is_read: Boolean,
        @SerializedName("user_id") val user_id: String,
       //  @SerializedName("category_name") val category_name: String,
        // @SerializedName("destination") val destination: String,
        @SerializedName("topic") val topic: String,
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
        send_at = send_at,
        is_read = is_read,
        user_id = user_id,
        //category_name = category_name,
        //destination = destination,
        topic = topic,
    )
