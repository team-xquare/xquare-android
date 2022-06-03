package com.xquare.domain.entity.auth

import org.threeten.bp.LocalDateTime

data class TokenEntity(
    val accessToken: String,
    val refreshToken: String,
    val expirationAt: LocalDateTime
)
