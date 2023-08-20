package com.xquare.data.local.preference

import org.threeten.bp.LocalDateTime

interface AuthPreference {

    suspend fun saveAccessToken(accessToken: String)

    suspend fun fetchAccessToken(): String

    suspend fun clearAccessToken()

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun fetchRefreshToken(): String

    suspend fun clearRefreshToken()

    suspend fun saveAccessTokenExpireAt(expiredAt: LocalDateTime)

    suspend fun fetchAccessTokenExpireAt(): LocalDateTime

    suspend fun clearAccessTokenExpireAt()

    suspend fun saveRefreshTokenExpireAt(expiredAt: LocalDateTime)

    suspend fun fetchRefreshTokenExpireAt(): LocalDateTime

    suspend fun clearRefreshTokenExpireAt()


    suspend fun fetchRole() : String

    suspend fun saveRole(role: String)

    suspend fun clearRole()

    suspend fun saveUserId(userId: String)

    suspend fun fetchUserId(): String

    suspend fun clearUserId()
}