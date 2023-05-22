package com.xquare.data.repository.reports

import com.xquare.data.remote.datasource.ReportRemoteDataSource
import com.xquare.domain.entity.reports.BugEntity
import com.xquare.domain.entity.reports.ReleaseEntity
import com.xquare.domain.repository.reports.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportRemoteDataSource: ReportRemoteDataSource
): ReportRepository {

    override suspend fun fetchRelease(): ReleaseEntity =
        reportRemoteDataSource.fetchRelease()

    override suspend fun uploadBug(bugEntity: BugEntity) =
        reportRemoteDataSource.uploadBug(bugEntity)
}