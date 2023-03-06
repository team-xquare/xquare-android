package com.xquare.domain.repository.schedules

import com.xquare.domain.entity.schedules.SchedulesEntity

interface SchedulesRepository {
    suspend fun fetchSchedules(month: Int): SchedulesEntity
}