package com.xquare.data.remote.response.user

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.user.HomeUserEntity

data class UserSimpleResponse(
    @SerializedName("name") val name: String,
    @SerializedName("profile_file_name") val profileFileName: String,
    @SerializedName("good_point") val goodPoint: Int,
    @SerializedName("bad_point") val badPoint: Int,
)

fun UserSimpleResponse.toEntity() = HomeUserEntity(
    name = name,
    profileFileImage = profileFileName,
    goodPoint = goodPoint,
    badPoint = badPoint,
)
