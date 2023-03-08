package com.xquare.domain.entity.schedules

data class SchedulesEntity(
    val schedules: List<SchedulesDataEntity>
) {
    data class SchedulesDataEntity(
        val id: String,
        val name: String,
        val date: String,
    )
}
