package com.xquare.domain.usecase.pick

import com.xquare.domain.repository.pick.PickRepository
import com.xquare.domain.usecase.UseCase
import java.time.LocalTime
import javax.inject.Inject

class BackToClassRoomUseCase @Inject constructor(
    private val pickRepository: PickRepository
): UseCase<Unit, Unit>() {
    override suspend fun execute(data: Unit) =
        pickRepository.backToClassRoom()
}