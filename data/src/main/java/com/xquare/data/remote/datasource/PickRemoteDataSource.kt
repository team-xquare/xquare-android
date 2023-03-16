package com.xquare.data.remote.datasource

import com.xquare.domain.entity.pick.PassTimeEntity

interface PickRemoteDataSource {
    suspend fun fetchPassTime(): PassTimeEntity
}