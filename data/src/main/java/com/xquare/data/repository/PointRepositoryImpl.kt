package com.xquare.data.repository

import com.xquare.data.remote.datasource.PointRemoteDataSource
import com.xquare.domain.entity.point.PointHistoriesEntity
import com.xquare.domain.repository.PointRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    private val pointRemoteDataSource: PointRemoteDataSource,
) : PointRepository {

    override suspend fun fetchPointSummary(): Flow<PointHistoriesEntity> {
        return flow { emit(pointRemoteDataSource.fetchPointSummary()) }
    }

    override suspend fun fetchGoodPointHistories(): Flow<PointHistoriesEntity> {
        return flow { emit(pointRemoteDataSource.fetchGoodPointHistories()) }
    }

    override suspend fun fetchBadPointHistories(): Flow<PointHistoriesEntity> {
        return flow { emit(pointRemoteDataSource.fetchBadPointHistories()) }
    }
}