package com.xquare.data.remote.api

import com.xquare.data.remote.request.schedules.WriteSchedulesRequest
import com.xquare.data.remote.response.schedules.SchedulesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SchedulesApi {
    @GET("schedules/school")
    suspend fun fetchSchedules(
        @Query("month") month: Int
    ): SchedulesResponse

    @POST("schedules/mine")
    suspend fun createSchedules(
        @Body writeSchedules: WriteSchedulesRequest
    )
}