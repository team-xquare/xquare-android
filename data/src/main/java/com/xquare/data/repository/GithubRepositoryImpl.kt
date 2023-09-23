package com.xquare.data.repository

import com.xquare.data.remote.datasource.github.GithubRemoteDataSource
import com.xquare.domain.entity.github.GithubInformationEntity
import com.xquare.domain.entity.github.GithubListEntity
import com.xquare.domain.entity.github.GithubOAuthCheckEntity
import com.xquare.domain.repository.GithubRepository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubRemoteDataSource: GithubRemoteDataSource,
): GithubRepository {

    override suspend fun fetchGithubOAuth(code: String) =
        githubRemoteDataSource.fetchGithubOAuth(code)

    override suspend fun fetchGithubInformation(): GithubInformationEntity =
        githubRemoteDataSource.fetchGithubInformation()

    override suspend fun fetchGithubList(): GithubListEntity =
        githubRemoteDataSource.fetchGithubList()

    override suspend fun fetchGithubOAuthCheck(): GithubOAuthCheckEntity =
        githubRemoteDataSource.fetchGithubOAuthCheck()
}