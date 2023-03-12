package com.xquare.data.remote.api

import com.xquare.data.remote.response.timetables.TimetablesResponse
import retrofit2.http.GET

interface TimetablesApi {
    @GET("timetables/week")
    suspend fun fetchWeekTimetables(): TimetablesResponse
}