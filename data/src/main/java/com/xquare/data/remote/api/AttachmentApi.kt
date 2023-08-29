package com.xquare.data.remote.api

import com.xquare.data.remote.response.attachment.FileResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AttachmentApi {
    @Multipart
    @POST("attachment")
    suspend fun uploadFile(
        @Part files: MultipartBody.Part
    ): FileResponse
}