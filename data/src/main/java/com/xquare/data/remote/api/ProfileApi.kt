package com.xquare.data.remote.api

import com.xquare.data.remote.response.profile.ProfileImageResponse
import com.xquare.data.remote.response.profile.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileApi {

    @Multipart
    @POST("users/images")
    suspend fun uploadProfileImage(
        @Part file: MultipartBody.Part
    ): ProfileImageResponse

    @GET("users")
    suspend fun fetchProfile(): ProfileResponse
}