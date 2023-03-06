package com.xquare.domain.usecase.user

import com.xquare.domain.repository.user.UserRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FixProfileImageUseCase @Inject constructor(
    private val userRepository: UserRepository
): UseCase<String?, Unit>() {
    override suspend fun execute(data: String?) =
        userRepository.fixProfileImage(data)
}