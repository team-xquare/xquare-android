package com.xquare.domain.usecase.user

import com.xquare.domain.entity.user.HomeUserEntity
import com.xquare.domain.repository.user.UserRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchUserSimpleDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<Unit, HomeUserEntity>() {

    override suspend fun execute(data: Unit): HomeUserEntity =
        userRepository.fetchUserSimpleData()
}