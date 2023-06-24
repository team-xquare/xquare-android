package com.xquare.domain.repository.reports

import com.xquare.domain.entity.reports.BugEntity
import com.xquare.domain.entity.reports.ReleaseEntity
import kotlinx.coroutines.flow.Flow

interface ReportRepository {

    suspend fun fetchRelease(): Flow<ReleaseEntity>

    suspend fun uploadBug(bugEntity: BugEntity)
}