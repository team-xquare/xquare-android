package com.xquare.domain.entity.auth

import org.threeten.bp.LocalDateTime

data class TokenEntity(
    val accessToken: String,
    val accessTokenExpireAt: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpireAt: LocalDateTime,
    val role: String
)
