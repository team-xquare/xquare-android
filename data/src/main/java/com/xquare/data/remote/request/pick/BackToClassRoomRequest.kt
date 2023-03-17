package com.xquare.data.remote.request.pick

import com.google.gson.annotations.SerializedName

data class BackToClassRoomRequest(
    @SerializedName("period") val period: Int
)
