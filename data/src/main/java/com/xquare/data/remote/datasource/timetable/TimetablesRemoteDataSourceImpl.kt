package com.xquare.data.remote.datasource.timetable

import android.os.Build
import androidx.annotation.RequiresApi
import com.xquare.data.remote.api.TimetablesApi
import com.xquare.data.remote.response.timetables.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.timetables.DayTimetableEntity
import com.xquare.domain.entity.timetables.TimetableEntity
import java.time.LocalDate
import javax.inject.Inject

class TimetablesRemoteDataSourceImpl @Inject constructor(
    private val timetablesApi: TimetablesApi,
): TimetablesRemoteDataSource {
    override suspend fun fetchWeekTimetables(): TimetableEntity =
        sendHttpRequest(
            httpRequest = { timetablesApi.fetchWeekTimetables().toEntity() }
        )

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchDayTimetables(): DayTimetableEntity {

        return sendHttpRequest(httpRequest = {
            timetablesApi.fetchDayTimetables(LocalDate.now()).toEntity()
        })
    }
}