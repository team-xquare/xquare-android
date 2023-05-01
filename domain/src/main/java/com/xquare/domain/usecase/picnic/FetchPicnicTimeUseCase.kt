package com.xquare.domain.usecase.picnic

import com.xquare.domain.entity.picnic.PicnicTimeEntity
import com.xquare.domain.repository.picnic.PicnicRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchPicnicTimeUseCase @Inject constructor(
    private val picnicRepository: PicnicRepository
): UseCase<Unit, PicnicTimeEntity>() {
    override suspend fun execute(data: Unit): PicnicTimeEntity =
        picnicRepository.fetchPicnicTime()

}