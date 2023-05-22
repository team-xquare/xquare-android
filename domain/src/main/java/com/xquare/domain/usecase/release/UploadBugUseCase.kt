package com.xquare.domain.usecase.release

import com.xquare.domain.entity.reports.BugEntity
import com.xquare.domain.repository.reports.ReportRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class UploadBugUseCase @Inject constructor(
    private val reportRepository: ReportRepository
): UseCase<BugEntity,Unit>() {
    override suspend fun execute(data: BugEntity) =
        reportRepository.uploadBug(data)

}