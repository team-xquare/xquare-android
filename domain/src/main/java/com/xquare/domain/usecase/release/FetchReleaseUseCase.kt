package com.xquare.domain.usecase.release

import com.xquare.domain.entity.reports.ReleaseEntity
import com.xquare.domain.repository.reports.ReportRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchReleaseUseCase @Inject constructor(
    private val reportRepository: ReportRepository
): UseCase<Unit,ReleaseEntity>() {
    override suspend fun execute(data: Unit): ReleaseEntity =
        reportRepository.fetchRelease()

}