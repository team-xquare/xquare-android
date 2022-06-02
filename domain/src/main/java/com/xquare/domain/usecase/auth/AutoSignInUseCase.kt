package com.xquare.domain.usecase.auth

import com.xquare.domain.repository.AuthRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class AutoSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<Unit, Unit>() {

    override suspend fun execute(data: Unit) =
        authRepository.autoSignIn()
}