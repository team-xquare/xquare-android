package com.xquare.data.repository.pick

import com.xquare.data.remote.datasource.PickRemoteDataSource
import com.xquare.domain.entity.pick.PassCheckEntity
import com.xquare.domain.repository.pick.PickRepository
import javax.inject.Inject

class PickRepositoryImpl @Inject constructor(
    private val pickRemoteDataSource: PickRemoteDataSource,
): PickRepository {
    override suspend fun fetchPassTime(): PassCheckEntity =
        pickRemoteDataSource.fetchPassTime()
}
