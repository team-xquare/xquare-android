package com.semicolon.domain.repository.user

import com.semicolon.domain.entity.user.HomeUserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchUserName(): Flow<HomeUserEntity>
}