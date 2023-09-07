package com.xquare.data.remote.datasource.report

import com.xquare.data.remote.api.ReportsApi
import com.xquare.data.remote.request.reports.toRequest
import com.xquare.data.remote.response.release.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.reports.BugEntity
import com.xquare.domain.entity.reports.ReleaseEntity
import javax.inject.Inject

class ReportRemoteDataSourceImpl @Inject constructor(
    private val reportsApi: ReportsApi
): ReportRemoteDataSource {

    override suspend fun uploadBug(bugEntity: BugEntity) =
        sendHttpRequest(httpRequest = { reportsApi.uploadBug(bugEntity.toRequest()) })

    override suspend fun fetchRelease(): ReleaseEntity =
        sendHttpRequest(httpRequest = { reportsApi.fetchRelease().toEntity() })
}