package com.xquare.data.remote.api

import com.xquare.data.remote.request.auth.SignInRequest
import com.xquare.data.remote.request.auth.SignUpRequest
import com.xquare.data.remote.response.auth.TokenResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApi {

    @POST("users/login")
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): TokenResponse

    @POST("users")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    )

    @PUT("users/login")
    suspend fun tokenRefresh(
        @Header("Refresh-Token") refreshToken: String
    ): TokenResponse
}