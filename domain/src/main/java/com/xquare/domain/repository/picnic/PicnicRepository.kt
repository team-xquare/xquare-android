package com.xquare.domain.repository.picnic

import com.xquare.domain.entity.picnic.PicnicTimeEntity

interface PicnicRepository {
    suspend fun fetchPicnicTime(): PicnicTimeEntity
}