package com.xquare.domain.repository.timetables

import com.xquare.domain.entity.timetables.TimetableEntity
import kotlinx.coroutines.flow.Flow

interface TimetablesRepository {
    suspend fun fetchWeekTimetables(): Flow<TimetableEntity>
}