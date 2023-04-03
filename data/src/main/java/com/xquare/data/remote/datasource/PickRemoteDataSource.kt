package com.xquare.data.remote.datasource

import com.xquare.domain.entity.pick.ClassPositionEntity
import com.xquare.domain.entity.pick.PassDataEntity
import com.xquare.domain.entity.pick.PassTimeEntity

interface PickRemoteDataSource {
    suspend fun fetchPassTime(): PassTimeEntity

    suspend fun fetchPassData(): PassDataEntity

    suspend fun backToClassRoom()

    suspend fun fetchClassPosition(): ClassPositionEntity
}