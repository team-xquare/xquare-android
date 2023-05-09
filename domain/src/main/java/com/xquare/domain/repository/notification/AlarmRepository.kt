package com.xquare.domain.repository.notification

import com.xquare.domain.entity.notification.AlarmEntity
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    suspend fun fetchAlarmHistory(): AlarmEntity
}