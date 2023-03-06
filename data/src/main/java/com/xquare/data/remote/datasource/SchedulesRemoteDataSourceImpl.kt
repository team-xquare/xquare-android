package com.xquare.data.remote.datasource

import android.util.Log
import com.xquare.data.remote.api.SchedulesApi
import com.xquare.data.remote.response.schedules.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.schedules.SchedulesEntity
import javax.inject.Inject

class SchedulesRemoteDataSourceImpl @Inject constructor(
    private val schedulesApi: SchedulesApi
): SchedulesRemoteDataSource {
    override suspend fun fetchSchedules(month: Int): SchedulesEntity =
        sendHttpRequest(
            httpRequest = {
                Log.d("TAG", "fetchSchedules: "+schedulesApi.fetchSchedules(month).toEntity())
                schedulesApi.fetchSchedules(month).toEntity()
            }
        )
}