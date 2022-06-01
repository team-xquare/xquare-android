package com.xquare.domain.repository.point

import com.xquare.domain.entity.point.DormitoryPointEntity
import kotlinx.coroutines.flow.Flow

interface PointRepository {

    suspend fun fetchDormitoryPoint(): Flow<DormitoryPointEntity>
}