package com.xquare.data.remote.response.pick

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.pick.PassDataEntity

data class PassDataResponse(
    @SerializedName("profile_file_name") val profile_file_name: String,
    @SerializedName("student_number") val student_number: String,
    @SerializedName("student_name") val student_name: String,
    @SerializedName("start_time") val start_time: String,
    @SerializedName("end_time") val end_time: String,
    @SerializedName("reason") val reason: String,
    @SerializedName("teacher_name") val teacher_name: String
)

fun PassDataResponse.toEntity() =
    PassDataEntity(
        profile_file_name = profile_file_name,
        student_number = student_number,
        student_name = student_name,
        start_time = start_time.substring(0..4),
        end_time = end_time.substring(0..4),
        reason = reason,
        teacher_name = teacher_name
    )



