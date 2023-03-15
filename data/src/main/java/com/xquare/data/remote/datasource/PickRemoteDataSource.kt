package com.xquare.data.remote.datasource

import com.xquare.domain.entity.pick.PassCheckEntity

interface PickRemoteDataSource {
    suspend fun fetchPassTime(): PassCheckEntity
}