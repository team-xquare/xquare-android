package com.xquare.domain.entity.pick

import java.time.LocalDate

data class PassDataEntity(
    val profile_file_name: String,
    val student_number: String,
    val student_name: String,
    val start_time: String,
    val end_time: String,
    val reason: String,
    val teacher_name: String,
    val picnic_date: String
)
