package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.BugApi
import com.xquare.data.remote.request.bug.toRequest
import com.xquare.data.remote.response.auth.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.auth.TokenEntity
import com.xquare.domain.entity.bug.BugEntity
import javax.inject.Inject

class BugRemoteDataSourceImpl @Inject constructor(
    private val bugApi: BugApi
): BugRemoteDataSource {

    override suspend fun uploadBug(bugEntity: BugEntity) =
        sendHttpRequest(httpRequest = { bugApi.uploadBug(bugEntity.toRequest()) })
}