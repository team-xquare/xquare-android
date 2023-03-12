package com.xquare.data.repository

import com.xquare.data.local.datasource.AuthLocalDataSource
import com.xquare.data.local.preference.AuthPreference
import com.xquare.data.remote.datasource.AuthRemoteDataSource
import com.xquare.domain.AppCookieManager
import com.xquare.domain.entity.auth.SignInEntity
import com.xquare.domain.entity.auth.SignUpEntity
import com.xquare.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authPreference: AuthPreference,
    private val appCookieManager: AppCookieManager,
) : AuthRepository {

    override suspend fun signIn(signInEntity: SignInEntity) {
        val token = authRemoteDataSource.signIn(signInEntity)
        val userId = signInEntity.accountId
        authLocalDataSource.saveToken(token)
        authPreference.saveUserId(userId)
        appCookieManager.writeToken(token)
    }

    override suspend fun signUp(signUpEntity: SignUpEntity) =
        authRemoteDataSource.signUp(signUpEntity)

    override suspend fun autoSignIn() {
        val refreshToken = authLocalDataSource.fetchToken().refreshToken
        val token = authRemoteDataSource.tokenRefresh("Bearer $refreshToken")
        authLocalDataSource.saveToken(token)
        appCookieManager.writeToken(token)
    }

    override suspend fun logout() {
        authPreference.clearAccessToken()
        authPreference.clearRefreshToken()
        authPreference.clearExpirationAt()
        authPreference.clearUserId()
    }
}