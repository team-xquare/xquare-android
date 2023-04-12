package com.xquare.domain.usecase.user

import com.xquare.domain.entity.user.HomeUserEntity
import com.xquare.domain.repository.user.UserRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUserSimpleDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<Unit, Flow<HomeUserEntity>>() {

    override suspend fun execute(data: Unit): Flow<HomeUserEntity> =
        userRepository.fetchUserSimpleData()
}