package com.xquare.data.remote.datasource.github

import com.xquare.data.remote.api.GithubApi
import com.xquare.data.remote.response.github.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.github.GithubInformationEntity
import com.xquare.domain.entity.github.GithubListEntity
import com.xquare.domain.entity.github.GithubOAuthCheckEntity
import com.xquare.domain.entity.github.GithubOAuthEntity
import javax.inject.Inject

class GithubRemoteDataSourceImpl @Inject constructor(
    private val githubApi: GithubApi
): GithubRemoteDataSource {


    override suspend fun fetchGithubOAuth(code: String)  =
        sendHttpRequest(httpRequest = { githubApi.githubOAuth(code) })

    override suspend fun fetchGithubInformation(): GithubInformationEntity =
        sendHttpRequest(httpRequest = { githubApi.githubInformation().toEntity() })

    override suspend fun fetchGithubOAuthCheck(): GithubOAuthCheckEntity =
        sendHttpRequest(httpRequest =  { githubApi.githubOAuthCheck().toEntity() })

    override suspend fun fetchGithubList(): GithubListEntity =
        sendHttpRequest(httpRequest = { githubApi.githubList().toEntity() })

}