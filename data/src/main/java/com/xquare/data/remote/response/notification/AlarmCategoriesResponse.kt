package com.xquare.data.remote.response.notification

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.notification.AlarmCategoriesEntity

data class AlarmCategoriesResponse(
    @SerializedName("settings") val categories: List<AlarmCategoryResponse>
){
    data class AlarmCategoryResponse(
        @SerializedName("topic") val topic: String,
        @SerializedName("is_activate") val isActivate: Boolean,
    )
}



fun AlarmCategoriesResponse.toEntity() =
    AlarmCategoriesEntity(
        categories = categories.map { it.toEntity() }
    )



fun AlarmCategoriesResponse.AlarmCategoryResponse.toEntity() =
    AlarmCategoriesEntity.AlarmCategoryEntity(
        topic = topic,
        isActivate = isActivate
    )