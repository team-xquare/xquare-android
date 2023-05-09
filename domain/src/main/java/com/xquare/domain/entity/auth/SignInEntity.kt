package com.xquare.domain.entity.auth

data class SignInEntity(
    val accountId: String,
    val password: String,
    val device_token: String
)