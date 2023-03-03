package com.xquare.data.remote.api

import com.xquare.data.remote.response.user.UserSimpleResponse
import retrofit2.http.GET

interface UserApi {

    @GET("users/simple")
    suspend fun fetchUserSimpleData(): UserSimpleResponse
}