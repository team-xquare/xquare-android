package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.SchedulesApi
import com.xquare.data.remote.request.schedules.WriteSchedulesRequest
import com.xquare.data.remote.request.schedules.toRequest
import com.xquare.data.remote.response.schedules.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.schedules.WriteSchedulesEntity
import java.time.LocalDate
import javax.inject.Inject

class SchedulesRemoteDataSourceImpl @Inject constructor(
    private val schedulesApi: SchedulesApi
): SchedulesRemoteDataSource {
    override suspend fun fetchSchedules(month: Int): SchedulesEntity =
        sendHttpRequest(
            httpRequest = { schedulesApi.fetchSchedules(month).toEntity() }
        )

    override suspend fun createSchedules(data: WriteSchedulesEntity) =
        sendHttpRequest(
            httpRequest = { schedulesApi.createSchedules(data.toRequest()) }
        )
}