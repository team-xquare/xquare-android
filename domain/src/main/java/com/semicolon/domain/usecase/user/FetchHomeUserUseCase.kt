package com.semicolon.domain.usecase.user

import com.semicolon.domain.entity.user.HomeUserEntity
import com.semicolon.domain.repository.user.UserRepository
import com.semicolon.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchHomeUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<Unit, Flow<HomeUserEntity>>() {

    override suspend fun execute(data: Unit): Flow<HomeUserEntity> =
        userRepository.fetchUserName()
}