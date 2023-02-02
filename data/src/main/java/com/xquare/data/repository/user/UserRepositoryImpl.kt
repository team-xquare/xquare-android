package com.xquare.data.repository.user

import com.xquare.data.remote.datasource.UserSimpleRemoteDataSource
import com.xquare.domain.entity.user.HomeUserEntity
import com.xquare.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserSimpleRemoteDataSource,
) : UserRepository{

    override suspend fun fetchUserSimpleData(): HomeUserEntity =
        userRemoteDataSource.fetchUserSimpleData()
}