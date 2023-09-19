package com.xquare.domain.usecase.auth

import com.xquare.domain.repository.AuthRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<Unit, String>() {

    override suspend fun execute(data: Unit): String =
        authRepository.fetchId()
}