package com.xquare.domain.usecase.timetables

import com.xquare.domain.entity.timetables.TimetableEntity
import com.xquare.domain.repository.timetables.TimetablesRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchWeekTimetablesUseCase @Inject constructor(
    private val timetablesRepository: TimetablesRepository,
): UseCase<Unit, TimetableEntity>() {
    override suspend fun execute(data: Unit): TimetableEntity =
        timetablesRepository.fetchWeekTimetables()
}