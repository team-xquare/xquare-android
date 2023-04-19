package com.xquare.data.remote.api

import com.xquare.data.remote.response.pick.ClassPositionResponse
import com.xquare.data.remote.response.pick.PassDataResponse
import com.xquare.data.remote.response.pick.PassTimeResponse
import com.xquare.data.remote.response.pick.TodaySelfStudyTeacherResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query


interface PickApi {

    @GET("pick/applications/return")
    suspend fun fetchPassTime(): PassTimeResponse

    @GET("pick/applications/picnic")
    suspend fun fetchPassData(): PassDataResponse

    @DELETE("pick/applications")
    suspend fun backToClassRoom()

    @GET("pick/class-room/location")
    suspend fun fetchClassPosition(): ClassPositionResponse


    @GET("pick/admin/director")
    suspend fun fetchTodaySelfStudyTeacher(
        @Query("month") month: String
    ) : TodaySelfStudyTeacherResponse

}