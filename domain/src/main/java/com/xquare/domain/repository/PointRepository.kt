package com.xquare.domain.repository

import com.xquare.domain.entity.point.PointHistoriesEntity
import kotlinx.coroutines.flow.Flow

interface PointRepository {
    suspend fun fetchPointHistories(offlineOnly: Boolean, pointType: Int): Flow<PointHistoriesEntity>
}