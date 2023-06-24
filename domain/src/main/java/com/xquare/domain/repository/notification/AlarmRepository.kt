package com.xquare.domain.repository.notification

import com.xquare.domain.entity.notification.ActivateAlarmEntity
import com.xquare.domain.entity.notification.AlarmCategoriesEntity
import com.xquare.domain.entity.notification.AlarmEntity
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    suspend fun fetchAlarmHistory(): Flow<AlarmEntity>

    suspend fun activateAlarm(isActivate: Boolean, topic: String)

    suspend fun fetchAlarmCategories(): Flow<AlarmCategoriesEntity>
}