package com.xquare.data.repository.pick

import com.xquare.data.remote.datasource.PickRemoteDataSource
import com.xquare.domain.entity.pick.PassDataEntity
import com.xquare.domain.entity.pick.PassTimeEntity
import com.xquare.domain.repository.pick.PickRepository
import javax.inject.Inject

class PickRepositoryImpl @Inject constructor(
    private val pickRemoteDataSource: PickRemoteDataSource,
): PickRepository {
    override suspend fun fetchPassTime(): PassTimeEntity =
        pickRemoteDataSource.fetchPassTime()

    override suspend fun fetchPassData(): PassDataEntity =
        pickRemoteDataSource.fetchPassData()
}
