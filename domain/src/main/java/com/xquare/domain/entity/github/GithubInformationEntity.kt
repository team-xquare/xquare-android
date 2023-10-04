package com.xquare.domain.entity.github

data class GithubInformationEntity(
    val userId: String,
    val name: String,
    val username: String,
    val profileFilename: String,
    val contributions: Int,
    val ranking: Int,
)
