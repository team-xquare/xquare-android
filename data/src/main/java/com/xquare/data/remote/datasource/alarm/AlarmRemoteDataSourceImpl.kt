package com.xquare.data.remote.datasource.alarm

import com.xquare.data.remote.api.NotificationApi
import com.xquare.data.remote.datasource.alarm.AlarmRemoteDataSource
import com.xquare.data.remote.response.notification.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.notification.AlarmCategoriesEntity
import com.xquare.domain.entity.notification.AlarmEntity
import javax.inject.Inject

class AlarmRemoteDataSourceImpl @Inject constructor(
    private val notificationApi: NotificationApi,
): AlarmRemoteDataSource {
    override suspend fun fetchAlarm(): AlarmEntity =
        sendHttpRequest(httpRequest = { notificationApi.fetchAlarm().toEntity() })

    override suspend fun activateAlarm(isActivate: Boolean, topic: String) =
        sendHttpRequest(httpRequest = { notificationApi.activateAlarm(isActivate = isActivate, topic = topic) })

    override suspend fun fetchAlarmCategories(): AlarmCategoriesEntity =
        sendHttpRequest(httpRequest = { notificationApi.fetchAlarmCategories().toEntity() })


}