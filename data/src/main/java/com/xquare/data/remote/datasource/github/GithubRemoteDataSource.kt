package com.xquare.data.remote.datasource.github

import com.xquare.domain.entity.github.GithubInformationEntity
import com.xquare.domain.entity.github.GithubListEntity
import com.xquare.domain.entity.github.GithubOAuthCheckEntity
import com.xquare.domain.entity.github.GithubOAuthEntity
import kotlinx.coroutines.flow.Flow

interface GithubRemoteDataSource {

    suspend fun fetchGithubOAuth(code: String)

    suspend fun fetchGithubInformation(): GithubInformationEntity

    suspend fun fetchGithubOAuthCheck(): GithubOAuthCheckEntity

    suspend fun fetchGithubList(): GithubListEntity
}