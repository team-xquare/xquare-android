package com.xquare.domain.entity.notification

import java.time.LocalDateTime

data class AlarmEntity(
    val notifications: List<AlarmDataEntity>
) {
    data class AlarmDataEntity(
        val id: String,
        val title: String,
        val content: String,
        val send_at: String,
        val is_read: Boolean,
        val user_id: String,
        //val category_name: String,
        //val destination: String,
        val topic: String,
    )
}
