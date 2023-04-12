package com.xquare.data.local.datasource

import com.xquare.domain.entity.point.PointHistoriesEntity

interface PointLocalDataSource {

    suspend fun fetchPoint(pointType: Int): PointHistoriesEntity

    suspend fun savePoint(pointHistoriesEntity: PointHistoriesEntity)
}