package com.xquare.data.repository

import com.xquare.data.local.datasource.AuthLocalDataSource
import com.xquare.data.remote.datasource.auth.AuthRemoteDataSource
import com.xquare.domain.repository.WebViewRepository
import javax.inject.Inject

class WebViewRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
) : WebViewRepository {

    override suspend fun fetchAuthorizationHeaders(): Map<String, String> =
        mapOf(
            Pair("Authorization", authLocalDataSource.fetchToken().accessToken)
        )

    override suspend fun refreshAuthorizationHeaders(): Map<String, String> {
        val refreshToken = authLocalDataSource.fetchToken().refreshToken
        val newToken = authRemoteDataSource.tokenRefresh(refreshToken)
        authLocalDataSource.saveToken(newToken)
        return mapOf(
            Pair("Authorization", newToken.accessToken)
        )
    }
}