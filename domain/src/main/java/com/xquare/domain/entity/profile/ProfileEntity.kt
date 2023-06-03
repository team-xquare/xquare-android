package com.xquare.domain.entity.profile


data class ProfileEntity(
    val accountId: String,
    val name: String,
    val birthday: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val profileFileName: String?
)