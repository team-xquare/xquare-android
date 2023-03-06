package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.UserApi
import com.xquare.data.remote.request.user.ProfileImageRequest
import com.xquare.data.remote.response.user.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.user.HomeUserEntity
import javax.inject.Inject

class UserSimpleRemoteDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
): UserSimpleRemoteDataSource {

    override suspend fun fetchUserSimpleData(): HomeUserEntity =
        sendHttpRequest(httpRequest = { userApi.fetchUserSimpleData().toEntity() })

    override suspend fun fixProfileImage(image: String?) =
        sendHttpRequest(httpRequest = { userApi.fixProfileImage(ProfileImageRequest(image)) })
}