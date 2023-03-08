package com.xquare.data.remote.api

import com.xquare.data.remote.request.user.ProfileImageRequest
import com.xquare.data.remote.response.user.UserSimpleResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserApi {

    @GET("users/simple")
    suspend fun fetchUserSimpleData(): UserSimpleResponse

    @PATCH("users")
    suspend fun fixProfileImage(
        @Body profileImageRequest: ProfileImageRequest
    )
}