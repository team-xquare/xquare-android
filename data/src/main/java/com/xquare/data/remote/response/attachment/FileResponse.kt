package com.xquare.data.remote.response.attachment

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.attachment.FileEntity

data class FileResponse(
    @SerializedName("fileUrl") val file_url: List<String>
)

fun FileResponse.toEntity() =
    FileEntity(file_url = file_url)
