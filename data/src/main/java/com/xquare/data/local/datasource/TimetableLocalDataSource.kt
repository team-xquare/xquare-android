package com.xquare.data.local.datasource

import com.xquare.domain.entity.timetables.TimetableEntity

interface TimetableLocalDataSource {

    suspend fun fetchTimetable(): TimetableEntity

    suspend fun saveTimetable(timetableData:TimetableEntity)
}