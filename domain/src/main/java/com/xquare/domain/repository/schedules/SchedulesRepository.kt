package com.xquare.domain.repository.schedules

import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.schedules.CreateSchedulesEntity
import com.xquare.domain.entity.schedules.FixSchedulesEntity
import kotlinx.coroutines.flow.Flow

interface SchedulesRepository {
    suspend fun fetchSchedules(month: Int): SchedulesEntity

    suspend fun createSchedules(data: CreateSchedulesEntity)

    suspend fun fixSchedules(data: FixSchedulesEntity)

    suspend fun deleteSchedules(id: String)
}