package com.xquare.domain.usecase.bug

import com.xquare.domain.entity.bug.BugEntity
import com.xquare.domain.repository.bug.BugRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class UploadBugUseCase @Inject constructor(
    private val bugRepository: BugRepository
): UseCase<BugEntity,Unit>() {
    override suspend fun execute(data: BugEntity) =
        bugRepository.uploadBug(data)

}