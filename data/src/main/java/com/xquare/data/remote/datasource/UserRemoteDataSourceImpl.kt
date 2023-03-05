package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.ProfileApi
import com.xquare.data.remote.response.profile.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.profile.ProfileEntity
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val profileApi: ProfileApi,
) : UserRemoteDataSource {

    override suspend fun fetchProfile(): ProfileEntity = sendHttpRequest(
        httpRequest = { profileApi.fetchProfile().toEntity() }
    )
}