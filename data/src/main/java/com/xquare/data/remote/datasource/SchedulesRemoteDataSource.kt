package com.xquare.data.remote.datasource

import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.schedules.CreateSchedulesEntity
import com.xquare.domain.entity.schedules.FixSchedulesEntity

interface SchedulesRemoteDataSource {
    suspend fun fetchSchedules(month: Int): SchedulesEntity

    suspend fun createSchedules(data: CreateSchedulesEntity)

    suspend fun fixSchedules(data: FixSchedulesEntity)
}