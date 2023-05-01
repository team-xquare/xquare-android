package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.PicnicApi
import com.xquare.data.remote.response.picnic.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.picnic.PicnicTimeEntity
import javax.inject.Inject

class PicnicRemoteDataSourceImpl @Inject constructor(
    private val picnicApi: PicnicApi
):PicnicRemoteDataSource{
    override suspend fun fetchPicnicPoint(): PicnicTimeEntity =
        sendHttpRequest(
            httpRequest = { picnicApi.fetchPicnicTime().toEntity() }
        )
}