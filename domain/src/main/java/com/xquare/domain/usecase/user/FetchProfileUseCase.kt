package com.xquare.domain.usecase.user

import com.xquare.domain.entity.profile.ProfileEntity
import com.xquare.domain.repository.user.UserRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
): UseCase<Unit, Flow<ProfileEntity>>() {

    override suspend fun execute(data: Unit): Flow<ProfileEntity> =
        userRepository.fetchProfile()
}