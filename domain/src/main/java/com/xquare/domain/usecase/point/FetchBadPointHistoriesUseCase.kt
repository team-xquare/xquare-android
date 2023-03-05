package com.xquare.domain.usecase.point

import com.xquare.domain.entity.point.PointHistoriesEntity
import com.xquare.domain.repository.PointRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchBadPointHistoriesUseCase @Inject constructor(
    private val pointRepository: PointRepository,
) : UseCase<Unit, Flow<PointHistoriesEntity>>() {

    override suspend fun execute(data: Unit): Flow<PointHistoriesEntity> =
        pointRepository.fetchBadPointHistories()
}