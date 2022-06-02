package com.xquare.domain.entity.auth

import org.threeten.bp.LocalDate
import java.io.File

data class SignUpEntity(
    val authCode: String,
    val accountId: String,
    val password: String,
    val name: String,
    val birthday: LocalDate,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val profileImage: File
)