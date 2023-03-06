package com.xquare.domain.entity.schedules

import java.time.LocalDate

data class FixSchedulesEntity(
    val id: String,
    val name: String,
    val date: LocalDate
)
