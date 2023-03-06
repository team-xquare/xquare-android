package com.xquare.domain.repository.timetables

import com.xquare.domain.entity.timetables.TimetableEntity

interface TimetablesRepository {
    suspend fun fetchWeekTimetables(): TimetableEntity
}