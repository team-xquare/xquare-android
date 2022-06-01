package com.xquare.data.repository.point

import com.xquare.domain.entity.point.DormitoryPointEntity
import com.xquare.domain.repository.point.PointRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(

) : PointRepository {

    override suspend fun fetchDormitoryPoint(): Flow<DormitoryPointEntity> {
        TODO("Not yet implemented")
    }
}