package com.xquare.data.remote.datasource

import com.xquare.domain.entity.notification.AlarmEntity

interface AlarmRemoteDataSource {
    suspend fun fetchAlarm(): AlarmEntity
}