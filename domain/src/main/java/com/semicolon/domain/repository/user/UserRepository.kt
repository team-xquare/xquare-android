package com.semicolon.domain.repository.user

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchUserName(): Flow<String>
}