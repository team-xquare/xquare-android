package com.xquare.domain.entity.schedules

import java.time.LocalDate

data class CreateSchedulesEntity(
    val name: String,
    val date: LocalDate
)
