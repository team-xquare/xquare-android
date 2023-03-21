package com.xquare.domain.usecase.schedules

import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.repository.schedules.SchedulesRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchSchedulesUseCase @Inject constructor(
    private val schedulesRepository: SchedulesRepository
): UseCase<Int, Flow<SchedulesEntity>>() {
    override suspend fun execute(data: Int): Flow<SchedulesEntity> =
        schedulesRepository.fetchSchedules(data)
}