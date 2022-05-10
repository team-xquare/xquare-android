package com.semicolon.domain.repository.point

import com.semicolon.domain.entity.point.DormitoryPointEntity
import kotlinx.coroutines.flow.Flow

interface PointRepository {

    suspend fun fetchDormitoryPoint(): Flow<DormitoryPointEntity>
}