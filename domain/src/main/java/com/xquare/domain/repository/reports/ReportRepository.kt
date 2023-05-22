package com.xquare.domain.repository.reports

import com.xquare.domain.entity.reports.BugEntity
import com.xquare.domain.entity.reports.ReleaseEntity

interface ReportRepository {

    suspend fun fetchRelease(): ReleaseEntity

    suspend fun uploadBug(bugEntity: BugEntity)
}