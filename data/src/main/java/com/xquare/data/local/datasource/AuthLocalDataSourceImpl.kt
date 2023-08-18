package com.xquare.data.local.datasource

import com.xquare.data.local.preference.AuthPreference
import com.xquare.domain.entity.auth.TokenEntity
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val authPreference: AuthPreference
) : AuthLocalDataSource {
    override suspend fun fetchToken(): TokenEntity =
        with((authPreference)){
            TokenEntity(
                accessToken = fetchAccessToken(),
                accessTokenExpireAt = fetchAccessTokenExpireAt(),
                refreshToken = fetchRefreshToken(),
                refreshTokenExpireAt = fetchRefreshTokenExpireAt(),
                expirationAt = fetchExpirationAt()
            )
        }



    override suspend fun saveToken(tokenEntity: TokenEntity) =
        with(authPreference) {
            saveAccessToken(tokenEntity.accessToken)
            saveAccessTokenExpireAt(tokenEntity.accessTokenExpireAt)
            saveRefreshToken(tokenEntity.refreshToken)
            saveAccessTokenExpireAt(tokenEntity.refreshTokenExpireAt)
            saveExpirationAt(tokenEntity.expirationAt)
        }

    override suspend fun clearToken() =
        with(authPreference) {
            clearAccessToken()
            clearRefreshToken()
            clearExpirationAt()
            clearAccessTokenExpireAt()
            clearRefreshTokenExpireAt()
        }
}
