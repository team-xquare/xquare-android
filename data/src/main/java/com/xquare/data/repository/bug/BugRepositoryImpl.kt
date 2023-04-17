package com.xquare.data.repository.bug

import com.xquare.data.remote.datasource.BugRemoteDataSource
import com.xquare.domain.entity.bug.BugEntity
import com.xquare.domain.repository.bug.BugRepository
import javax.inject.Inject

class BugRepositoryImpl @Inject constructor(
    private val bugRemoteDataSource: BugRemoteDataSource
): BugRepository {
    override suspend fun uploadBug(bugEntity: BugEntity) =
        bugRemoteDataSource.uploadBug(bugEntity)
}