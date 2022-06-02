package com.xquare.data.local.preference

import org.threeten.bp.LocalDateTime

interface AuthPreference {

    suspend fun saveAccessToken(accessToken: String)

    suspend fun fetchAccessToken(): String

    suspend fun clearAccessToken()

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun fetchRefreshToken(): String

    suspend fun clearRefreshToken()

    suspend fun saveExpirationAt(expiredAt: LocalDateTime)

    suspend fun fetchExpirationAt(): LocalDateTime

    suspend fun clearExpirationAt()
}