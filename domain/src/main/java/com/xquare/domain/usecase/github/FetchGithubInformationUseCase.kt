package com.xquare.domain.usecase.github

import com.xquare.domain.entity.github.GithubInformationEntity
import com.xquare.domain.repository.GithubRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchGithubInformationUseCase @Inject constructor(
    private val githubRepository: GithubRepository
): UseCase<Unit, GithubInformationEntity>() {

    override suspend fun execute(data: Unit): GithubInformationEntity =
        githubRepository.fetchGithubInformation()

}