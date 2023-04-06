package com.xquare.data.local.datasource

import com.xquare.domain.entity.point.PointHistoriesEntity

interface PointLocalDataSource {

    suspend fun fetchPoint(): PointHistoriesEntity

    suspend fun savePoint(pointHistoriesEntity: PointHistoriesEntity)
}