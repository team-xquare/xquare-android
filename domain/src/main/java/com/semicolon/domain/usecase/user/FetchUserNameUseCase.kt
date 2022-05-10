package com.semicolon.domain.usecase.user

import com.semicolon.domain.repository.user.UserRepository
import com.semicolon.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUserNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<Unit, Flow<String>>() {

    override suspend fun execute(data: Unit): Flow<String> =
        userRepository.fetchUserName()
}