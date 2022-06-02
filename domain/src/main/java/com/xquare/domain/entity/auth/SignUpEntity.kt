package com.xquare.domain.entity.auth

import java.io.File

data class SignUpEntity(
    val authCode: String,
    val accountId: String,
    val password: String,
    val profileImage: File?
)