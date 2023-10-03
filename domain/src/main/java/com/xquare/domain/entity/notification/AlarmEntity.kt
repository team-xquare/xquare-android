package com.xquare.domain.entity.notification


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
        val topic: String,
    )
}
