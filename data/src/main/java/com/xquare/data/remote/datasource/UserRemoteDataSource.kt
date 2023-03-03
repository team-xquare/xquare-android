package com.xquare.data.remote.datasource

import com.xquare.domain.entity.profile.ProfileEntity

interface UserRemoteDataSource {

    suspend fun fetchProfile(): ProfileEntity
}