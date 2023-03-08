package com.xquare.data.repository.timetables

import com.xquare.data.remote.datasource.TimetablesRemoteDataSource
import com.xquare.domain.entity.timetables.TimetableEntity
import com.xquare.domain.repository.timetables.TimetablesRepository
import javax.inject.Inject

class TimetablesRepositoryImpl @Inject constructor(
    private val timetablesRemoteDataSource: TimetablesRemoteDataSource,
): TimetablesRepository {
    override suspend fun fetchWeekTimetables(): TimetableEntity =
        timetablesRemoteDataSource.fetchWeekTimetables()
}