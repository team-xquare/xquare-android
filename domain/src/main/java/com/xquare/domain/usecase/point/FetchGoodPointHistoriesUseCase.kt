package com.xquare.domain.usecase.point

import com.xquare.domain.entity.point.PointHistoriesEntity
import com.xquare.domain.repository.PointRepository
import com.xquare.domain.repository.meal.MealRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchGoodPointHistoriesUseCase @Inject constructor(
    private val pointRepository: PointRepository
) : UseCase<Boolean, Flow<PointHistoriesEntity>>() {

    override suspend fun execute(data: Boolean): Flow<PointHistoriesEntity> =
        pointRepository.fetchPointHistories(offlineOnly = data, pointType = 0)
}