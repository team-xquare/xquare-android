package com.xquare.data.remote.datasource.auth

import com.xquare.data.remote.api.AuthApi
import com.xquare.data.remote.api.ProfileApi
import com.xquare.data.remote.request.auth.toRequest
import com.xquare.data.remote.response.auth.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.data.toMultipart
import com.xquare.domain.entity.auth.SignInEntity
import com.xquare.domain.entity.auth.SignUpEntity
import com.xquare.domain.entity.auth.TokenEntity
import com.xquare.domain.exception.ImageBadRequestException
import com.xquare.domain.exception.UnknownException
import com.xquare.domain.exception.UnsupportedMediaTypeException
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApi: AuthApi,
    private val profileApi: ProfileApi
) : AuthRemoteDataSource {

    override suspend fun signIn(signInEntity: SignInEntity): TokenEntity =
        sendHttpRequest(httpRequest = { authApi.signIn(signInEntity.toRequest()).toEntity() })


    override suspend fun signUp(signUpEntity: SignUpEntity) {
        val profileImageUrl = signUpEntity.profileImage?.let {
            sendHttpRequest(
                httpRequest = {
                    profileApi
                        .uploadProfileImage(it.toMultipart())
                        .imageUrl
                },
                onBadRequest = {
                    ImageBadRequestException()
                },
                onOtherHttpStatusCode = { code, _ ->
                    if (code == 415) UnsupportedMediaTypeException() else UnknownException()
                })
        }
        sendHttpRequest(httpRequest = { authApi.signUp(signUpEntity.toRequest(profileImageUrl)) })
    }

    override suspend fun tokenRefresh(refreshToken: String): TokenEntity =
        sendHttpRequest(httpRequest = { authApi.tokenRefresh(refreshToken).toEntity() })
}