package com.xquare.domain.entity.user

data class HomeUserEntity(
    val profileFileImage: String,
    val name: String,
    val goodPoint: Int,
    val badPoint: Int,
)