package com.xquare.data.local.datasource

import com.xquare.domain.entity.auth.TokenEntity
import org.threeten.bp.LocalDateTime

interface AuthLocalDataSource {

    suspend fun fetchToken(): TokenEntity

    suspend fun saveToken(tokenEntity: TokenEntity)

    suspend fun clearToken()
    fun TokenEntity(
        accessToken: String,
        accessTokenExpireAt: LocalDateTime,
        refreshToken: String,
        refreshTokenExpireAt: LocalDateTime,
        expirationAt: LocalDateTime
    ): TokenEntity
}