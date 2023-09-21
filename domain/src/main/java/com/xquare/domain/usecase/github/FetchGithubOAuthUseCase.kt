package com.xquare.domain.usecase.github

import com.xquare.domain.entity.github.GithubOAuthEntity
import com.xquare.domain.repository.GithubRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchGithubOAuthUseCase @Inject constructor(

    private val githubRepository: GithubRepository
) : UseCase<GithubOAuthEntity, Unit>() {

    override suspend fun execute(data: GithubOAuthEntity) =
        githubRepository.fetchGithubOAuth(code = data.code)

}
