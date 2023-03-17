package com.xquare.data.remote.api

import com.xquare.data.remote.request.pick.BackToClassRoomRequest
import com.xquare.data.remote.response.pick.ClassPositionResponse
import com.xquare.data.remote.response.pick.PassDataResponse
import com.xquare.data.remote.response.pick.PassTimeResponse
import retrofit2.http.DELETE
import retrofit2.http.GET

interface PickApi {

    @GET("pick/applications/return")
    suspend fun fetchPassTime(): PassTimeResponse

    @GET("pick/applications/picnic")
    suspend fun fetchPassData(): PassDataResponse

    @DELETE("pick/applications")
    suspend fun backToClassRoom(
        body: BackToClassRoomRequest
    )

    @GET("pick/class-room/location")
    suspend fun fetchClassPosition(): ClassPositionResponse
}