package com.xquare.data.remote.api

import com.xquare.data.remote.response.timetables.DayTimetablesResponse
import com.xquare.data.remote.response.timetables.TimetablesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import java.time.LocalDate

interface TimetablesApi {
    @GET("timetables/week")
    suspend fun fetchWeekTimetables(): TimetablesResponse

    @GET("timetables/{date}")
    suspend fun fetchDayTimetables(
        @Path("date") date: LocalDate
    ): DayTimetablesResponse
}