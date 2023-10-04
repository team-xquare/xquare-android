package com.xquare.data.remote.response.github

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.github.GithubInformationEntity

data class GithubInformationResponse(
    @SerializedName("user_id") val userId: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("profile_file_name") val profileFilename: String,
    @SerializedName("contributions") val contributions: Int,
    @SerializedName("ranking") val ranking: Int,
)

fun GithubInformationResponse.toEntity(): GithubInformationEntity =
    GithubInformationEntity(
        userId = userId,
        name = name,
        username = username,
        profileFilename = profileFilename,
        contributions = contributions,
        ranking = ranking
    )
