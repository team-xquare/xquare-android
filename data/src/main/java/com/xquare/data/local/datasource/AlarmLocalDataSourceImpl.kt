package com.xquare.data.local.datasource

import com.xquare.data.dao.AlarmDao
import com.xquare.data.local.entity.alarm.toEntity
import com.xquare.data.local.entity.alarm.toRoomEntity
import com.xquare.domain.entity.notification.AlarmEntity
import javax.inject.Inject

class AlarmLocalDataSourceImpl @Inject constructor(
    private val alarmDao: AlarmDao
): AlarmLocalDataSource {
    override suspend fun fetchAlarm(): AlarmEntity =
        alarmDao.fetchAlarm().toEntity()

    override suspend fun saveAlarm(data: AlarmEntity) =
        alarmDao.updateAlarm(data.toRoomEntity())
}