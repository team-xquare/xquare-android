package com.xquare.domain.usecase.schedules

import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.repository.schedules.SchedulesRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchSchedulesUseCase @Inject constructor(
    private val schedulesRepository: SchedulesRepository
): UseCase<Int, SchedulesEntity>() {
    override suspend fun execute(data: Int): SchedulesEntity =
        schedulesRepository.fetchSchedules(data)
}