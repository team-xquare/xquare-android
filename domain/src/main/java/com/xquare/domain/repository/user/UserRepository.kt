package com.xquare.domain.repository.user

import com.xquare.domain.entity.profile.ProfileEntity
import com.xquare.domain.entity.user.HomeUserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchProfile(): Flow<ProfileEntity>

    suspend fun fetchUserSimpleData(): Flow<HomeUserEntity>

    suspend fun fixProfileImage(image: String?)
}