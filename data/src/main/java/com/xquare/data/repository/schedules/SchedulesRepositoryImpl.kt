package com.xquare.data.repository.schedules

import com.xquare.data.fetchDataWithOfflineCache
import com.xquare.data.local.datasource.ScheduleLocalDataSource
import com.xquare.data.remote.datasource.SchedulesRemoteDataSource
import com.xquare.data.today
import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.schedules.CreateSchedulesEntity
import com.xquare.domain.entity.schedules.FixSchedulesEntity
import com.xquare.domain.repository.schedules.SchedulesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SchedulesRepositoryImpl @Inject constructor(
    private val schedulesRemoteDataSource: SchedulesRemoteDataSource,
    private val schedulesLocalDataSource: ScheduleLocalDataSource,
): SchedulesRepository {

    val date = today()
    val month = date.monthValue
    override suspend fun fetchSchedules(month: Int): Flow<SchedulesEntity> {
        return fetchDataWithOfflineCache(
            fetchLocalData = { schedulesLocalDataSource.fetchSchedule() },
            fetchRemoteData = { schedulesRemoteDataSource.fetchSchedules(month) },
            refreshLocalData = { schedulesLocalDataSource.saveSchedule(it) },
            offlineOnly = true
        )
    }

    override suspend fun createSchedules(data: CreateSchedulesEntity) =
        schedulesRemoteDataSource.createSchedules(data)

    override suspend fun fixSchedules(data: FixSchedulesEntity) =
        schedulesRemoteDataSource.fixSchedules(data)

    override suspend fun deleteSchedules(id: String) =
        schedulesRemoteDataSource.deleteSchedules(id)
}