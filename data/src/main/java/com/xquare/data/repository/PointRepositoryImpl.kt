package com.xquare.data.repository

import com.xquare.data.fetchDataWithOfflineCache
import com.xquare.data.local.datasource.PointLocalDataSource
import com.xquare.data.remote.datasource.PointRemoteDataSource
import com.xquare.domain.entity.point.PointHistoriesEntity
import com.xquare.domain.repository.PointRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    private val pointRemoteDataSource: PointRemoteDataSource,
    private val pointLocalDataSource: PointLocalDataSource,
) : PointRepository {

    override suspend fun fetchPointSummary(): Flow<PointHistoriesEntity> =
        fetchDataWithOfflineCache(
            fetchLocalData = { pointLocalDataSource.fetchPoint() },
            fetchRemoteData = { pointRemoteDataSource.fetchPointSummary() },
            refreshLocalData = {pointLocalDataSource.savePoint(it) },
            offlineOnly = false
        )

    override suspend fun fetchGoodPointHistories(): Flow<PointHistoriesEntity> =
        fetchDataWithOfflineCache(
            fetchRemoteData = { pointRemoteDataSource.fetchGoodPointHistories() },
            fetchLocalData = { pointLocalDataSource.fetchPoint()},
            refreshLocalData = { pointLocalDataSource.savePoint(it) },
            offlineOnly = false
        )


    override suspend fun fetchBadPointHistories(): Flow<PointHistoriesEntity> =
        fetchDataWithOfflineCache(
            fetchRemoteData = { pointRemoteDataSource.fetchBadPointHistories() },
            fetchLocalData = { pointLocalDataSource.fetchPoint()},
            refreshLocalData = { pointLocalDataSource.savePoint(it) },
            offlineOnly = false
        )
}