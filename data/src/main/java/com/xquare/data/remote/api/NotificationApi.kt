package com.xquare.data.remote.api

import com.xquare.data.remote.response.notification.AlarmResponse
import retrofit2.http.GET

interface NotificationApi {
    @GET("notifications")
    suspend fun fetchAlarm(): AlarmResponse
}