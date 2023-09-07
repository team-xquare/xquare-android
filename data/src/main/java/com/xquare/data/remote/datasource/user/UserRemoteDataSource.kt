package com.xquare.data.remote.datasource.user

import com.xquare.domain.entity.profile.ProfileEntity

interface UserRemoteDataSource {

    suspend fun fetchProfile(): ProfileEntity
}