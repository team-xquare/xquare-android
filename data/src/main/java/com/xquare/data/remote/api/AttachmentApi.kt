package com.xquare.data.remote.api

import com.xquare.data.remote.request.attachment.FetchPreSignedUrlRequest
import com.xquare.data.remote.response.attachment.FetchPreSignedUrlResponse
import retrofit2.http.Multipart
import retrofit2.http.POST

interface AttachmentApi {
    @Multipart
    @POST("attachment")
    suspend fun fetchPreSignedUrl(
        fetchPreSignedUrlRequest: FetchPreSignedUrlRequest,
    ): FetchPreSignedUrlResponse
}