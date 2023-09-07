package com.xquare.data.repository

import com.xquare.data.fetchPointWithOfflineCache
import com.xquare.data.local.datasource.PointLocalDataSource
import com.xquare.data.remote.datasource.point.PointRemoteDataSource
import com.xquare.domain.entity.point.PointHistoriesEntity
import com.xquare.domain.repository.PointRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    private val pointRemoteDataSource: PointRemoteDataSource,
    private val pointLocalDataSource: PointLocalDataSource,
) : PointRepository {

    override suspend fun fetchPointHistories(
        offlineOnly: Boolean,
        pointType: Int
    ): Flow<PointHistoriesEntity> {
        return fetchPointWithOfflineCache(
            fetchRemoteData = { pointRemoteDataSource.fetchPointSummary() },
            fetchLocalData = { pointLocalDataSource.fetchPoint(pointType = pointType) },
            refreshLocalData = { pointLocalDataSource.savePoint(it) },
            offlineOnly = offlineOnly
        )
    }
}