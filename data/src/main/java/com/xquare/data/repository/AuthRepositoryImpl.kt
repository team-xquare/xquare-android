package com.xquare.data.repository

import com.xquare.data.local.datasource.AuthLocalDataSource
import com.xquare.data.remote.datasource.AuthRemoteDataSource
import com.xquare.domain.entity.auth.SignInEntity
import com.xquare.domain.entity.auth.SignUpEntity
import com.xquare.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun signIn(signInEntity: SignInEntity) {
        val token = authRemoteDataSource.signIn(signInEntity)
        authLocalDataSource.saveToken(token)
    }

    override suspend fun signUp(signUpEntity: SignUpEntity) =
        authRemoteDataSource.signUp(signUpEntity)
}