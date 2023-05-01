package com.xquare.data.repository.picnic

import com.xquare.data.remote.datasource.PicnicRemoteDataSource
import com.xquare.domain.entity.picnic.PicnicTimeEntity
import com.xquare.domain.repository.picnic.PicnicRepository
import javax.inject.Inject

class PicnicRepositoryImpl @Inject constructor(
    private val picnicRemoteDataSource: PicnicRemoteDataSource
): PicnicRepository{
    override suspend fun fetchPicnicTime(): PicnicTimeEntity =
        picnicRemoteDataSource.fetchPicnicPoint()

}