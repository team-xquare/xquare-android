package com.xquare.domain.usecase.timetables

import com.xquare.domain.entity.timetables.TimetableEntity
import com.xquare.domain.repository.timetables.TimetablesRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchWeekTimetablesUseCase @Inject constructor(
    private val timetablesRepository: TimetablesRepository,
): UseCase<Unit, Flow<TimetableEntity>>() {
    override suspend fun execute(data: Unit): Flow<TimetableEntity> =
        timetablesRepository.fetchWeekTimetables()
}