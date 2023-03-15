package com.xquare.domain.usecase.pick

import com.xquare.domain.entity.pick.PassCheckEntity
import com.xquare.domain.repository.pick.PickRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchPassTimeUseCase @Inject constructor(
    private val pickRepository: PickRepository
) : UseCase<Unit, PassCheckEntity>() {
    override suspend fun execute(data: Unit): PassCheckEntity =
        pickRepository.fetchPassTime()
}