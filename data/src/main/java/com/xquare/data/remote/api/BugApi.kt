package com.xquare.data.remote.api

import com.xquare.data.remote.request.bug.BugRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BugApi {

    @POST("reports/report")
    suspend fun uploadBug(
        @Body bugRequest: BugRequest
    )
}