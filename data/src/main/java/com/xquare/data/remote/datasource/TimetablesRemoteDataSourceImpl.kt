package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.TimetablesApi
import com.xquare.data.remote.response.timetables.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.timetables.TimetableEntity
import javax.inject.Inject

class TimetablesRemoteDataSourceImpl @Inject constructor(
    private val timetablesApi: TimetablesApi,
): TimetablesRemoteDataSource {
    override suspend fun fetchWeekTimetables(): TimetableEntity =
        sendHttpRequest(
            httpRequest = { timetablesApi.fetchWeekTimetables().toEntity() }
        )
}