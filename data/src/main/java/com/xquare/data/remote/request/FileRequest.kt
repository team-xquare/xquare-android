package com.xquare.data.remote.request

import com.google.gson.annotations.SerializedName
import java.io.File

data class FileRequest(
    @SerializedName("image_file_requests") val files: List<File>
)
