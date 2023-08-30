package com.xquare.data.remote.api

import com.xquare.data.remote.request.attachment.FetchPreSignedUrlRequest
import com.xquare.data.remote.response.attachment.FetchPreSignedUrlResponse
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST

interface AttachmentApi {
    @POST("attachment")
    suspend fun fetchPreSignedUrl(
        @Body fetchPreSignedUrlRequest: FetchPreSignedUrlRequest,
    ): FetchPreSignedUrlResponse
}