package com.xquare.domain.entity.notification

data class AlarmCategoriesEntity(
    val categories: List<AlarmCategoryEntity>
){
    data class AlarmCategoryEntity(
        val topic: String,
        val isActivate: Boolean,
    )
}
