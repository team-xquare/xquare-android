package com.xquare.data.repository.user

import com.xquare.domain.entity.user.HomeUserEntity
import com.xquare.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(

) : UserRepository{

    override suspend fun fetchUserName(): Flow<HomeUserEntity> {
        TODO("Not yet implemented")
    }
}