package com.xquare.data.remote.response.profile

import com.google.gson.annotations.SerializedName

data class ProfileImageResponse(
    @SerializedName("image_url") val image_url: String
)