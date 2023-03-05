package com.xquare.data.remote.api

import com.xquare.data.remote.response.point.PointHistoriesResponse
import retrofit2.http.GET

interface PointApi {

    @GET("points/history?type=ALL")
    suspend fun fetchPointSummary(): PointHistoriesResponse

    @GET("pointsstory?type=GOODPOINT")
    suspend fun fetchGoodPointHistories(): PointHistoriesResponse

    @GET("pointsstory?type=BADPOINT")
    suspend fun fetchBadPointHistories(): PointHistoriesResponse
}