package com.xquare.data.repository.timetables

import com.xquare.data.fetchDataWithOfflineCache
import com.xquare.data.local.datasource.TimetableLocalDataSource
import com.xquare.data.remote.datasource.TimetablesRemoteDataSource
import com.xquare.data.today
import com.xquare.domain.entity.timetables.TimetableEntity
import com.xquare.domain.repository.timetables.TimetablesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimetablesRepositoryImpl @Inject constructor(
    private val timetablesRemoteDataSource: TimetablesRemoteDataSource,
    private val timetablesLocalDataSource: TimetableLocalDataSource
): TimetablesRepository {
    override suspend fun fetchWeekTimetables(): Flow<TimetableEntity> =
        fetchDataWithOfflineCache(
            fetchLocalData = { timetablesLocalDataSource.fetchTimetable() },
            fetchRemoteData = { timetablesRemoteDataSource.fetchWeekTimetables() },
            refreshLocalData = { timetablesLocalDataSource.saveTimetable(it) },
            offlineOnly = false
        )
}