package com.xquare.domain.usecase.point

import com.xquare.domain.entity.point.DormitoryPointEntity
import com.xquare.domain.repository.point.PointRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDormitoryPointUseCase @Inject constructor(
    private val pointRepository: PointRepository
) : UseCase<Unit, Flow<DormitoryPointEntity>>() {

    override suspend fun execute(data: Unit): Flow<DormitoryPointEntity> =
        pointRepository.fetchDormitoryPoint()
}