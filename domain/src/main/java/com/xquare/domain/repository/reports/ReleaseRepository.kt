package com.xquare.domain.repository.reports

import com.xquare.domain.entity.reports.ReleaseEntity

interface ReleaseRepository {
    suspend fun fetchRelease(): ReleaseEntity
}