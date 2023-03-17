package com.xquare.data.repository.timetables

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.xquare.data.remote.datasource.TimetablesRemoteDataSource
import com.xquare.domain.entity.timetables.DayTimetableEntity
import com.xquare.domain.entity.timetables.TimetableEntity
import com.xquare.domain.repository.timetables.TimetablesRepository
import java.time.LocalTime
import javax.inject.Inject

class TimetablesRepositoryImpl @Inject constructor(
    private val timetablesRemoteDataSource: TimetablesRemoteDataSource,
): TimetablesRepository {
    override suspend fun fetchWeekTimetables(): TimetableEntity =
        timetablesRemoteDataSource.fetchWeekTimetables()

    override suspend fun fetchDayTimetables(): DayTimetableEntity =
        timetablesRemoteDataSource.fetchDayTimetables()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchPeriod(): Int =
        returnPeriod(timetablesRemoteDataSource.fetchDayTimetables())


    @RequiresApi(Build.VERSION_CODES.O)
    private fun returnPeriod(dayTimetableEntity: DayTimetableEntity): Int {
        val thisTime = LocalTime.now()
        var period = 10
        val timeArray  = dayTimetableEntity.times.toTypedArray()
        for (it in timeArray) {
            if (thisTime.isBefore(it.begin_time)) {
                period = it.period - 1
                break
            }
        }


        return period
    }
}