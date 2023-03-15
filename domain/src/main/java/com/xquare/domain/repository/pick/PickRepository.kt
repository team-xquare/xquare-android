package com.xquare.domain.repository.pick

import com.xquare.domain.entity.pick.PassCheckEntity

interface PickRepository {
    suspend fun fetchPassTime(): PassCheckEntity
}