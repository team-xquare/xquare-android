package com.xquare.data.repository.notification

import com.xquare.data.fetchDataWithOfflineCache
import com.xquare.data.local.datasource.AlarmLocalDataSource
import com.xquare.data.remote.datasource.AlarmRemoteDataSource
import com.xquare.domain.entity.notification.AlarmEntity
import com.xquare.domain.repository.notification.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmRemoteDataSource: AlarmRemoteDataSource,
    private val alarmLocalDataSource: AlarmLocalDataSource,
): AlarmRepository {
    override suspend fun fetchAlarm(): Flow<AlarmEntity> =
        fetchDataWithOfflineCache(
            fetchLocalData = { alarmLocalDataSource.fetchAlarm() },
            fetchRemoteData = { alarmRemoteDataSource.fetchAlarm() },
            refreshLocalData = { alarmLocalDataSource.saveAlarm(it) },
            offlineOnly = true,
        )
}