package com.xquare.domain.usecase.auth

import com.xquare.domain.entity.auth.SignInEntity
import com.xquare.domain.repository.AuthRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<SignInEntity, Unit>() {

    override suspend fun execute(data: SignInEntity) =
        authRepository.signIn(data)
}