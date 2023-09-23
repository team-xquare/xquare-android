package com.xquare.data.remote.api

import com.xquare.data.remote.response.github.GithubInformationResponse
import com.xquare.data.remote.response.github.GithubListResponse
import com.xquare.data.remote.response.github.GithubOAuthCheckResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface GithubApi {

    @POST("gits")
    suspend fun githubOAuth(
        @Query("code") code: String
    )

    @PATCH("gits")
    suspend fun githubUpdateRanking()

    @GET("gits")
    suspend fun githubInformation(): GithubInformationResponse

    @GET("gits/all")
    suspend fun githubList(): GithubListResponse

    @GET("gits/exist")
    suspend fun githubOAuthCheck(): GithubOAuthCheckResponse

}