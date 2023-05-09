package com.xquare.data.remote.api

import com.xquare.data.remote.response.auth.TokenResponse
import com.xquare.data.remote.response.notification.AlarmResponse
import com.xquare.domain.entity.auth.TokenEntity
import retrofit2.http.GET
import retrofit2.http.Header

interface NotificationApi {
    @GET("notifications/list")
    suspend fun fetchAlarm(): AlarmResponse
}