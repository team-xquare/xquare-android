package com.xquare.data.remote.datasource.report

import com.xquare.domain.entity.reports.BugEntity
import com.xquare.domain.entity.reports.ReleaseEntity

interface ReportRemoteDataSource {

    suspend fun uploadBug(bugEntity: BugEntity)

    suspend fun fetchRelease(): ReleaseEntity
}