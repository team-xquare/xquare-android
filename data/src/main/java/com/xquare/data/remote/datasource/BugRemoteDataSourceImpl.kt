package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.ReportsApi
import com.xquare.data.remote.request.reports.toRequest
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.reports.BugEntity
import javax.inject.Inject

class BugRemoteDataSourceImpl @Inject constructor(
    private val reportsApi: ReportsApi
): BugRemoteDataSource {

    override suspend fun uploadBug(bugEntity: BugEntity) =
        sendHttpRequest(httpRequest = { reportsApi.uploadBug(bugEntity.toRequest()) })
}