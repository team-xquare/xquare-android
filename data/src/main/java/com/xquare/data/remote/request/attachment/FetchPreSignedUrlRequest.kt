package com.xquare.data.remote.request.attachment

import com.google.gson.annotations.SerializedName

data class FetchPreSignedUrlRequest(
    @SerializedName("image_file_requests") val requests: List<ImageFileRequest>,
) {
    data class ImageFileRequest(
        @SerializedName("original_filename") val originalFilename: String,
        @SerializedName("content_type") val contentType: String,
        @SerializedName("file_size") val fileSize: Long,
    )
}
