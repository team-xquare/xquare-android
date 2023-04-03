package com.xquare.data.remote.datasource

import com.xquare.domain.entity.timetables.DayTimetableEntity
import com.xquare.domain.entity.timetables.TimetableEntity

interface TimetablesRemoteDataSource {
    suspend fun fetchWeekTimetables(): TimetableEntity

    suspend fun fetchDayTimetables(): DayTimetableEntity
}