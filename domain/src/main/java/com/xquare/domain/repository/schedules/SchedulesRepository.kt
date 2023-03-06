package com.xquare.domain.repository.schedules

import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.schedules.WriteSchedulesEntity

interface SchedulesRepository {
    suspend fun fetchSchedules(month: Int): SchedulesEntity

    suspend fun createSchedules(data: WriteSchedulesEntity)
}