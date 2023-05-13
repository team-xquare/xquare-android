package com.xquare.domain.repository.reports

import com.xquare.domain.entity.reports.BugEntity

interface BugRepository {
    suspend fun uploadBug(bugEntity: BugEntity)
}