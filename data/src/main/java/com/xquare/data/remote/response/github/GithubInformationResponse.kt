package com.xquare.data.remote.response.github

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.github.GithubInformationEntity

data class GithubInformationResponse(
    @SerializedName("user_id") val user_id: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("profile_file_name") val profile_file_name: String,
    @SerializedName("contributions") val contributions: Int,
    @SerializedName("ranking") val ranking: Int,
)

fun GithubInformationResponse.toEntity(): GithubInformationEntity =
    GithubInformationEntity(
        user_id = user_id,
        name = name,
        username = username,
        profile_file_name = profile_file_name,
        contributions = contributions,
        ranking = ranking
    )
