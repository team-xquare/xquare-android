package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.PointApi
import com.xquare.data.remote.response.point.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.point.PointHistoriesEntity
import com.xquare.domain.entity.point.PointSummaryEntity
import javax.inject.Inject

class PointRemoteDataSourceImpl @Inject constructor(
    private val pointApi: PointApi,
) : PointRemoteDataSource {

    override suspend fun fetchPointSummary(): PointHistoriesEntity =
        sendHttpRequest(
            httpRequest = { pointApi.fetchPointSummary().toEntity() }
        )

    override suspend fun fetchGoodPointHistories(): PointHistoriesEntity =
        sendHttpRequest(
            httpRequest = { pointApi.fetchGoodPointHistories().toEntity() }
        )

    override suspend fun fetchBadPointHistories(): PointHistoriesEntity =
        sendHttpRequest(
            httpRequest = { pointApi.fetchBadPointHistories().toEntity() }
        )
}