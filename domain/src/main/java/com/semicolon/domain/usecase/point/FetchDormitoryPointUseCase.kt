package com.semicolon.domain.usecase.point

import com.semicolon.domain.entity.point.DormitoryPointEntity
import com.semicolon.domain.repository.point.PointRepository
import com.semicolon.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDormitoryPointUseCase @Inject constructor(
    private val pointRepository: PointRepository
) : UseCase<Unit, Flow<DormitoryPointEntity>>() {

    override suspend fun execute(data: Unit): Flow<DormitoryPointEntity> =
        pointRepository.fetchDormitoryPoint()
}