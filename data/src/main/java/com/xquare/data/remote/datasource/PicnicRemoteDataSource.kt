package com.xquare.data.remote.datasource

import com.xquare.domain.entity.picnic.PicnicTimeEntity

interface PicnicRemoteDataSource {
    suspend fun fetchPicnicPoint():PicnicTimeEntity
}