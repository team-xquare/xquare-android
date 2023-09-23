package com.xquare.domain.usecase.github

import com.xquare.domain.entity.github.GithubOAuthCheckEntity
import com.xquare.domain.repository.GithubRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchGithubOAuthCheckUseCase @Inject constructor(
    private val githubRepository: GithubRepository
) : UseCase<Unit, GithubOAuthCheckEntity>() {

    override suspend fun execute(data: Unit): GithubOAuthCheckEntity =
        githubRepository.fetchGithubOAuthCheck()

}
