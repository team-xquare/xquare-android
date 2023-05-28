package com.xquare.data.remote.api

import com.xquare.data.remote.request.reports.BugRequest
import com.xquare.data.remote.response.release.ReleaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ReportsApi {

    @POST("reports/report")
    suspend fun uploadBug(
        @Body bugRequest: BugRequest
    )

    @GET("reports/note/list")
    suspend fun fetchRelease(): ReleaseResponse
}