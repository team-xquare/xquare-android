package com.xquare.data.local.datasource

import com.xquare.domain.entity.notification.AlarmEntity

interface AlarmLocalDataSource {
    suspend fun fetchAlarm(): AlarmEntity

    suspend fun saveAlarm(data: AlarmEntity)
}