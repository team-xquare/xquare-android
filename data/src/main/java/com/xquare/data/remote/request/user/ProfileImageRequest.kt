package com.xquare.data.remote.request.user

import com.google.gson.annotations.SerializedName

data class ProfileImageRequest(
    @SerializedName("profile_file_name") val profile_file_name: String?
)