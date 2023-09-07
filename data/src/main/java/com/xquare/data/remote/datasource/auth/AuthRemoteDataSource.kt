package com.xquare.data.remote.datasource.auth

import com.xquare.domain.entity.auth.SignInEntity
import com.xquare.domain.entity.auth.SignUpEntity
import com.xquare.domain.entity.auth.TokenEntity

interface AuthRemoteDataSource {

    suspend fun signIn(signInEntity: SignInEntity): TokenEntity

    suspend fun signUp(signUpEntity: SignUpEntity)

    suspend fun tokenRefresh(refreshToken: String): TokenEntity
}