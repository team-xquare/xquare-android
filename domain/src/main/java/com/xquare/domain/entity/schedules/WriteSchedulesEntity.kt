package com.xquare.domain.entity.schedules

import java.time.LocalDate
import java.time.LocalDateTime

data class WriteSchedulesEntity(
    val name: String,
    val date: LocalDate
)
