package com.xquare.domain.repository

import com.xquare.domain.entity.auth.SignInEntity
import com.xquare.domain.entity.auth.SignUpEntity

interface AuthRepository {

    suspend fun signIn(signInEntity: SignInEntity)

    suspend fun signUp(signUpEntity: SignUpEntity)

    suspend fun autoSignIn(): Boolean

    suspend fun logout()

    suspend fun fetchId(): String
}