package com.xquare.domain.usecase.pick

import com.xquare.domain.entity.pick.PassDataEntity
import com.xquare.domain.repository.pick.PickRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchPassDataUseCase @Inject constructor(
    private val pickRepository: PickRepository,
): UseCase<Unit, PassDataEntity>() {
    override suspend fun execute(data: Unit): PassDataEntity =
        pickRepository.fetchPassData()
}