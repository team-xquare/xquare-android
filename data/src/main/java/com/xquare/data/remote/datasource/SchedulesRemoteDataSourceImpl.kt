package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.SchedulesApi
import com.xquare.data.remote.request.schedules.toRequest
import com.xquare.data.remote.response.schedules.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.schedules.CreateSchedulesEntity
import com.xquare.domain.entity.schedules.FixSchedulesEntity
import javax.inject.Inject

class SchedulesRemoteDataSourceImpl @Inject constructor(
    private val schedulesApi: SchedulesApi
): SchedulesRemoteDataSource {
    override suspend fun fetchSchedules(month: Int): SchedulesEntity =
        sendHttpRequest(
            httpRequest = { schedulesApi.fetchSchedules(month).toEntity() }
        )

    override suspend fun createSchedules(data: CreateSchedulesEntity) =
        sendHttpRequest(
            httpRequest = { schedulesApi.createSchedules(data.toRequest()) }
        )

    override suspend fun fixSchedules(data: FixSchedulesEntity) =
        sendHttpRequest(
            httpRequest = { schedulesApi.fixSchedules(id = data.id, data.toRequest()) }
        )

}