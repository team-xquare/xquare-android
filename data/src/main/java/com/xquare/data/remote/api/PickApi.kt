package com.xquare.data.remote.api

import com.xquare.data.remote.response.pick.PassCheckResponse
import retrofit2.http.GET

interface PickApi {

    @GET("pick/applications")
    suspend fun fetchPassTime(): PassCheckResponse
}