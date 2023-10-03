package com.xquare.data.remote.datasource.user

import com.xquare.domain.entity.user.HomeUserEntity
import kotlinx.coroutines.flow.Flow


interface UserSimpleRemoteDataSource {

    suspend fun fetchUserSimpleData(): HomeUserEntity

    suspend fun fixProfileImage(image: String?)
}