package com.xquare.data.remote.datasource

import com.xquare.domain.entity.reports.ReleaseEntity

interface ReleaseRemoteDataSource {
    suspend fun fetchRelease(): ReleaseEntity
}