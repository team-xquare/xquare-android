package com.xquare.domain.usecase.timetables

import com.xquare.domain.repository.timetables.TimetablesRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchPeriodUseCase @Inject constructor(
    private val timetablesRepository: TimetablesRepository
): UseCase<Unit, Int>() {
    override suspend fun execute(data: Unit): Int =
        timetablesRepository.fetchPeriod()
}