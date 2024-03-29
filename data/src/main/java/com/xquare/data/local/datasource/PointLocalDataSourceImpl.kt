package com.xquare.data.local.datasource

import com.xquare.data.dao.PointDao
import com.xquare.data.local.entity.point.toEntity
import com.xquare.data.local.entity.point.toRoomEntity
import com.xquare.domain.entity.point.PointHistoriesEntity
import javax.inject.Inject

class PointLocalDataSourceImpl @Inject constructor(
    private val pointDao: PointDao,
): PointLocalDataSource {
    override suspend fun fetchPoint(pointType: Int): PointHistoriesEntity =
        pointDao.fetchPoint().toEntity(pointType = pointType)

    override suspend fun savePoint(pointHistoriesEntity: PointHistoriesEntity) =
        pointDao.updatePoint(repo = pointHistoriesEntity.toRoomEntity())

}