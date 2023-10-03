package com.xquare.data.remote.datasource.alarm

import com.xquare.domain.entity.notification.ActivateAlarmEntity
import com.xquare.domain.entity.notification.AlarmCategoriesEntity
import com.xquare.domain.entity.notification.AlarmEntity

interface AlarmRemoteDataSource {
    suspend fun fetchAlarm(): AlarmEntity

    suspend fun activateAlarm(isActivate: Boolean, topic: String)

    suspend fun fetchAlarmCategories(): AlarmCategoriesEntity
}