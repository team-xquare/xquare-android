package com.xquare.data.remote.api

import com.xquare.data.remote.response.notification.AlarmCategoriesResponse
import com.xquare.data.remote.response.notification.AlarmResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface NotificationApi {
    @GET("notifications/list")
    suspend fun fetchAlarm(): AlarmResponse

    @PATCH("notifications/tags?is-activated=&topic=")
    suspend fun activateAlarm(
        @Query("is_activate") isActivate: Boolean,
        @Query("topic") topic: String,
    )

    @GET("notifications/tags")
    suspend fun fetchAlarmCategories(): AlarmCategoriesResponse
}