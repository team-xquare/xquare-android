package com.xquare.data.remote.api

import com.xquare.data.remote.response.pick.PassTimeResponse
import retrofit2.http.GET

interface PickApi {

    @GET("pick/applications/return")
    suspend fun fetchPassTime(): PassTimeResponse

    @GET("pick/applications/picnic")
    suspend fun fetchPass()
}