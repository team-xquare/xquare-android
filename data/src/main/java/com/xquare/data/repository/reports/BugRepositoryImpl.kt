package com.xquare.data.repository.reports

import com.xquare.data.remote.datasource.BugRemoteDataSource
import com.xquare.domain.entity.reports.BugEntity
import com.xquare.domain.repository.reports.BugRepository
import javax.inject.Inject

class BugRepositoryImpl @Inject constructor(
    private val bugRemoteDataSource: BugRemoteDataSource
): BugRepository {
    override suspend fun uploadBug(bugEntity: BugEntity) =
        bugRemoteDataSource.uploadBug(bugEntity)
}