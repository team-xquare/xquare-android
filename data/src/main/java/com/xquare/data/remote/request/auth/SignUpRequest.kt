package com.xquare.data.remote.request.auth

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.auth.SignUpEntity

data class SignUpRequest(
    @SerializedName("auth_code") val authCode: String,
    @SerializedName("account_id") val accountId: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("birth_day") val birthday: String,
    @SerializedName("grade") val grade: Int,
    @SerializedName("class_num") val classNum: Int,
    @SerializedName("num") val num: Int,
    @SerializedName("profile_image_url") val profileImageUrl: String
)

fun SignUpEntity.toRequest(profileImageUrl: String) = SignUpRequest(
    authCode = authCode,
    accountId = accountId,
    password = password,
    name = name,
    birthday = birthday.toString(),
    grade = grade,
    classNum = classNum,
    num = num,
    profileImageUrl = profileImageUrl
)