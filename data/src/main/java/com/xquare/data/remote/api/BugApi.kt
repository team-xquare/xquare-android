package com.xquare.data.remote.api

import com.xquare.data.remote.request.bug.BugRequest
import com.xquare.data.remote.response.auth.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BugApi {

    @POST("")
    suspend fun uploadBug(
        @Body bugRequest: BugRequest
    )
}