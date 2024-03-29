package com.xquare.data.remote.api

import com.xquare.data.remote.response.point.PointHistoriesResponse
import retrofit2.http.GET

interface PointApi {

    @GET("points/history?type=null")
    suspend fun fetchPointSummary(): PointHistoriesResponse

    @GET("points/history?type=GOODPOINT")
    suspend fun fetchGoodPointHistories(): PointHistoriesResponse

    @GET("points/history?type=BADPOINT")
    suspend fun fetchBadPointHistories(): PointHistoriesResponse
}