package com.xquare.data.remote.api

import com.xquare.data.remote.response.picnic.PicnicTimeResponse
import retrofit2.http.GET

interface PicnicApi {

    @GET("picnic/time")
    suspend fun fetchPicnicTime(): PicnicTimeResponse
}