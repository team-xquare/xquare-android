package com.xquare.data.repository.user

import com.xquare.data.remote.datasource.UserRemoteDataSource
import com.xquare.domain.entity.profile.ProfileEntity
import com.xquare.domain.entity.user.HomeUserEntity
import com.xquare.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {

    override suspend fun fetchUserName(): Flow<HomeUserEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchProfile(): Flow<ProfileEntity> =
        flow { emit(userRemoteDataSource.fetchProfile()) }
}