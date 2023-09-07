package com.xquare.data.remote.datasource.point

import com.xquare.domain.entity.point.PointHistoriesEntity

interface PointRemoteDataSource {

    suspend fun fetchPointSummary(): PointHistoriesEntity

    suspend fun fetchGoodPointHistories(): PointHistoriesEntity

    suspend fun fetchBadPointHistories(): PointHistoriesEntity
}