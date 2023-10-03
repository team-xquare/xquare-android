package com.xquare.data.repository.reports

import com.xquare.data.remote.datasource.report.ReportRemoteDataSource
import com.xquare.domain.entity.reports.BugEntity
import com.xquare.domain.entity.reports.ReleaseEntity
import com.xquare.domain.repository.reports.ReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportRemoteDataSource: ReportRemoteDataSource
): ReportRepository {

    override suspend fun fetchRelease(): Flow<ReleaseEntity> =
        flow { emit(reportRemoteDataSource.fetchRelease()) }

    override suspend fun uploadBug(bugEntity: BugEntity) =
        reportRemoteDataSource.uploadBug(bugEntity)
}