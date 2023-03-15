package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.PickApi
import com.xquare.data.remote.response.pick.toEntity
import com.xquare.domain.entity.pick.PassCheckEntity
import javax.inject.Inject

class PickRemoteDataSourceImpl @Inject constructor(
    private val pickApi: PickApi
): PickRemoteDataSource {
    override suspend fun fetchPassTime(): PassCheckEntity =
        pickApi.fetchPassTime().toEntity()
}