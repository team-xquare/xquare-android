package com.xquare.data.remote.request.auth

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.auth.SignInEntity

data class SignInRequest(
    @SerializedName("account_id") val accountId: String,
    @SerializedName("password") val password: String,
    @SerializedName("device_token") val deviceToken: String
)

fun SignInEntity.toRequest() = SignInRequest(
    accountId = accountId,
    password = password,
    deviceToken = "Temporary" // TODO()
)