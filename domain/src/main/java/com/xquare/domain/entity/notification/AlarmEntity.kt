package com.xquare.domain.entity.notification

import java.time.LocalDateTime

data class AlarmEntity(
    val notifications: List<AlarmDataEntity>
) {
    data class AlarmDataEntity(
        val id: String,
        val title: String,
        val content: String,
        val dateTime: LocalDateTime,
        val is_read: Boolean,
        val user_id: String,
        val name: String,
        val destination: String,
    )
}
