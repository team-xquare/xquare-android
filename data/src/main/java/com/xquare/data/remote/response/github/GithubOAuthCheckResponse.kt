package com.xquare.data.remote.response.github

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.github.GithubOAuthCheckEntity

data class GithubOAuthCheckResponse(
    @SerializedName("is_connected") val is_connected: Boolean,
)

fun GithubOAuthCheckResponse.toEntity(): GithubOAuthCheckEntity =
    GithubOAuthCheckEntity(
        is_connected = is_connected
    )