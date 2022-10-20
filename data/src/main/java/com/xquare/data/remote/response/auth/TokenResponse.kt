package com.xquare.data.remote.response.auth

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.auth.TokenEntity
import org.threeten.bp.LocalDateTime

data class TokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("expire_at") val expirationAt: String
)

fun TokenResponse.toEntity() = TokenEntity(
    accessToken = accessToken,
    refreshToken = refreshToken,
    expirationAt = LocalDateTime.parse(expirationAt)
)