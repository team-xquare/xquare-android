package com.xquare.data.remote.response.auth

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.auth.TokenEntity
import org.threeten.bp.LocalDateTime

data class TokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("access_token_expire_at") val accessTokenExpireAt: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_token_expire_at") val refreshTokenExpireAt: String,
    @SerializedName("expire_at") val expirationAt: String
)

fun TokenResponse.toEntity() = TokenEntity(
    accessToken = accessToken,
    accessTokenExpireAt = LocalDateTime.parse(accessTokenExpireAt),
    refreshToken = refreshToken,
    refreshTokenExpireAt = LocalDateTime.parse(refreshTokenExpireAt),
    expirationAt = LocalDateTime.parse(expirationAt)
)