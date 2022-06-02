package com.xquare.domain.usecase.auth

import com.xquare.domain.entity.auth.SignUpEntity
import com.xquare.domain.repository.AuthRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<SignUpEntity, Unit>() {

    override suspend fun execute(data: SignUpEntity) =
        authRepository.signUp(data)
}