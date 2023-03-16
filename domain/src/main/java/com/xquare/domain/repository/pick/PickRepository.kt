package com.xquare.domain.repository.pick

import com.xquare.domain.entity.pick.PassDataEntity
import com.xquare.domain.entity.pick.PassTimeEntity

interface PickRepository {
    suspend fun fetchPassTime(): PassTimeEntity

    suspend fun fetchPassData(): PassDataEntity
}