package com.xquare.domain.usecase.schedules

import com.xquare.domain.entity.schedules.FixSchedulesEntity
import com.xquare.domain.repository.schedules.SchedulesRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FixSchedulesUseCase @Inject constructor(
    private val schedulesRepository: SchedulesRepository
): UseCase<FixSchedulesEntity, Unit>() {
    override suspend fun execute(data: FixSchedulesEntity) =
        schedulesRepository.fixSchedules(data)
}