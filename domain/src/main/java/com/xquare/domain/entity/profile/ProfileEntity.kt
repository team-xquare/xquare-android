package com.xquare.domain.entity.profile

import org.threeten.bp.LocalDate

data class ProfileEntity(
    val accountId: String,
    val name: String,
    val birthday: LocalDate,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val profileFileName: String?
)