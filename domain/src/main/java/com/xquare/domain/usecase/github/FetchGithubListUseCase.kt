package com.xquare.domain.usecase.github

import com.xquare.domain.entity.github.GithubListEntity
import com.xquare.domain.repository.GithubRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchGithubListUseCase @Inject constructor(
    private val githubRepository: GithubRepository
): UseCase<Unit, GithubListEntity>() {

    override suspend fun execute(data: Unit): GithubListEntity =
        githubRepository.fetchGithubList()
}