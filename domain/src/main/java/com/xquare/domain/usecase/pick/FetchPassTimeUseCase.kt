package com.xquare.domain.usecase.pick

import com.xquare.domain.entity.pick.PassTimeEntity
import com.xquare.domain.repository.pick.PickRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchPassTimeUseCase @Inject constructor(
    private val pickRepository: PickRepository
) : UseCase<Unit, PassTimeEntity>() {
    override suspend fun execute(data: Unit): PassTimeEntity =
        pickRepository.fetchPassTime()
}