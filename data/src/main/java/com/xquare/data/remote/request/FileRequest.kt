package com.xquare.data.remote.request

import com.google.gson.annotations.SerializedName
import java.io.File

data class FileRequest(
    @SerializedName("files") val files: List<File>
)
