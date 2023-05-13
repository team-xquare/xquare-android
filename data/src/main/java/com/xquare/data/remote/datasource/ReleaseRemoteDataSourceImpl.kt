package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.ReportsApi
import com.xquare.data.remote.response.release.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.reports.ReleaseEntity
import javax.inject.Inject

class ReleaseRemoteDataSourceImpl @Inject constructor(
    private val reportsApi: ReportsApi
): ReleaseRemoteDataSource{
    override suspend fun fetchRelease(): ReleaseEntity =
        sendHttpRequest(httpRequest = { reportsApi.fetchRelease().toEntity() })
}