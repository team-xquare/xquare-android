package com.xquare.domain.usecase.pick

import com.xquare.domain.entity.pick.ClassPositionEntity
import com.xquare.domain.repository.pick.PickRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchClassPositionUseCase @Inject constructor(
    private val pickRepository: PickRepository
): UseCase<Unit, ClassPositionEntity>() {
    override suspend fun execute(data: Unit): ClassPositionEntity =
        pickRepository.fetchClassPosition()
}
