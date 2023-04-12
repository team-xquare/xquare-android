package com.xquare.data.repository.user

import com.xquare.data.fetchDataWithOfflineCache
import com.xquare.data.local.datasource.HomeUserLocalDataSource
import com.xquare.data.local.entity.homeUser.toRoomEntity
import com.xquare.data.remote.datasource.UserRemoteDataSource
import com.xquare.domain.entity.profile.ProfileEntity
import com.xquare.data.remote.datasource.UserSimpleRemoteDataSource
import com.xquare.domain.entity.user.HomeUserEntity
import com.xquare.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userSimpleRemoteDataSource: UserSimpleRemoteDataSource,
    private val userLocalDataSource: HomeUserLocalDataSource,
) : UserRepository {

    override suspend fun fetchProfile(): Flow<ProfileEntity> =
        flow { emit(userRemoteDataSource.fetchProfile()) }

    override suspend fun fetchUserSimpleData(): Flow<HomeUserEntity> =
        fetchDataWithOfflineCache(
            fetchRemoteData = { userSimpleRemoteDataSource.fetchUserSimpleData()},
            fetchLocalData = { userLocalDataSource.fetchHomeUser() },
            refreshLocalData = { userLocalDataSource.saveHomeUser(it) },
            offlineOnly = false
        )

    override suspend fun fixProfileImage(image: String?) =
        userSimpleRemoteDataSource.fixProfileImage(image)

}