package com.xquare.domain.repository.user

import com.xquare.domain.entity.user.HomeUserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchUserName(): Flow<HomeUserEntity>
}