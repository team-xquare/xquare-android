package com.xquare.data.remote.request.auth

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.auth.SignUpEntity

data class SignUpRequest(
    @SerializedName("verification_code") val authCode: String,
    @SerializedName("account_id") val accountId: String,
    @SerializedName("password") val password: String,
    @SerializedName("profile_file_name") val profileImageUrl: String?
)

fun SignUpEntity.toRequest(profileImageUrl: String?) = SignUpRequest(
    authCode = authCode,
    accountId = accountId,
    password = password,
    profileImageUrl = profileImageUrl
)