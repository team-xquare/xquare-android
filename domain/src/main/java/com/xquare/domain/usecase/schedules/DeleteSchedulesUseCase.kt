package com.xquare.domain.usecase.schedules

import com.xquare.domain.repository.schedules.SchedulesRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class DeleteSchedulesUseCase @Inject constructor(
    private val schedulesRepository: SchedulesRepository
): UseCase<String, Unit>() {
    override suspend fun execute(data: String) =
        schedulesRepository.deleteSchedules(data)
}