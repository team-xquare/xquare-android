package com.xquare.domain.repository

import com.xquare.domain.entity.point.PointHistoriesEntity
import kotlinx.coroutines.flow.Flow

interface PointRepository {

    suspend fun fetchPointSummary(): Flow<PointHistoriesEntity>

    suspend fun fetchGoodPointHistories(): Flow<PointHistoriesEntity>

    suspend fun fetchBadPointHistories(): Flow<PointHistoriesEntity>
}