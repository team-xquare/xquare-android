package com.xquare.data.remote.response.profile

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.profile.ProfileEntity
import org.threeten.bp.LocalDate

data class ProfileResponse(
    @SerializedName("account_id") val accountId: String,
    @SerializedName("name") val name: String,
    @SerializedName("birth_day") val birthday: String,
    @SerializedName("grade") val grade: Int,
    @SerializedName("class_num") val classNum: Int,
    @SerializedName("num") val num: Int,
    @SerializedName("profile_file_name") val profileFileName: String?,
)

fun ProfileResponse.toEntity() =
    ProfileEntity(
        accountId = accountId,
        name = name,
        birthday = LocalDate.parse(birthday),
        grade = grade,
        classNum = classNum,
        num = num,
        profileFileName = profileFileName
    )