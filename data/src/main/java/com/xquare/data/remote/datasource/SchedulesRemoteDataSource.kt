package com.xquare.data.remote.datasource

import com.xquare.domain.entity.schedules.SchedulesEntity

interface SchedulesRemoteDataSource {
    suspend fun fetchSchedules(month: Int): SchedulesEntity
}