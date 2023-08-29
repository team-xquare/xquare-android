package com.xquare.data.remote.response.attachment

import com.google.gson.annotations.SerializedName

// todo response 수정 고민
data class FetchPreSignedUrlResponse(
    @SerializedName("url") val url: String,
    @SerializedName("presigned_url") val preSignedUrl: String,
    @SerializedName("original_filename") val originalFilename: String,
    @SerializedName("content_type") val contentType: String,
)
