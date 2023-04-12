package com.xquare.data.local.datasource

import com.xquare.domain.entity.user.HomeUserEntity

interface HomeUserLocalDataSource {

    suspend fun fetchHomeUser(): HomeUserEntity

    suspend fun saveHomeUser(homeUserEntity: HomeUserEntity)
}