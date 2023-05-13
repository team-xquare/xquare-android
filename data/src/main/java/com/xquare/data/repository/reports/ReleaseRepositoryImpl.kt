package com.xquare.data.repository.reports

import com.xquare.data.remote.datasource.ReleaseRemoteDataSource
import com.xquare.domain.entity.reports.ReleaseEntity
import com.xquare.domain.repository.reports.ReleaseRepository
import javax.inject.Inject

class ReleaseRepositoryImpl @Inject constructor(
    private val releaseRemoteDataSource: ReleaseRemoteDataSource
): ReleaseRepository{
    override suspend fun fetchRelease(): ReleaseEntity =
        releaseRemoteDataSource.fetchRelease()
}