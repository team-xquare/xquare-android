package com.xquare.domain.usecase.schedules

import com.xquare.domain.entity.schedules.CreateSchedulesEntity
import com.xquare.domain.repository.schedules.SchedulesRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class CreateSchedulesUseCase @Inject constructor(
    private val schedulesRepository: SchedulesRepository
): UseCase<CreateSchedulesEntity, Unit>() {
    override suspend fun execute(data: CreateSchedulesEntity) =
        schedulesRepository.createSchedules(data)
}