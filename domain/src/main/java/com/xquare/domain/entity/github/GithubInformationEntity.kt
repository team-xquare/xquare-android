package com.xquare.domain.entity.github

data class GithubInformationEntity(
    val user_id: String,
    val name: String,
    val username: String,
    val profile_file_name: String,
    val contributions: Int,
    val ranking: Int,
)
