package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.PickApi
import com.xquare.data.remote.response.pick.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.pick.PassDataEntity
import com.xquare.domain.entity.pick.PassTimeEntity
import javax.inject.Inject

class PickRemoteDataSourceImpl @Inject constructor(
    private val pickApi: PickApi
): PickRemoteDataSource {
    override suspend fun fetchPassTime(): PassTimeEntity =
        sendHttpRequest(httpRequest = { pickApi.fetchPassTime().toEntity() })

    override suspend fun fetchPassData(): PassDataEntity =
        sendHttpRequest(httpRequest = { pickApi.fetchPassData().toEntity() })
}