package com.xquare.domain.repository

import com.xquare.domain.entity.github.GithubInformationEntity
import com.xquare.domain.entity.github.GithubListEntity
import com.xquare.domain.entity.github.GithubOAuthCheckEntity
import com.xquare.domain.entity.github.GithubOAuthEntity
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    suspend fun fetchGithubOAuth(code: String)

    suspend fun fetchGithubInformation(): GithubInformationEntity

    suspend fun fetchGithubList(): GithubListEntity

    suspend fun fetchGithubOAuthCheck(): GithubOAuthCheckEntity
}