package com.xquare.data.remote.datasource

import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.schedules.WriteSchedulesEntity

interface SchedulesRemoteDataSource {
    suspend fun fetchSchedules(month: Int): SchedulesEntity

    suspend fun createSchedules(data: WriteSchedulesEntity)
}