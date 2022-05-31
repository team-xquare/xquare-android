package com.xquare.data.remote.response.meal

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.meal.MealEntity

data class TodayMealResponse(
    @SerializedName("breakfast") val breakfast: List<String>,
    @SerializedName("lunch") val lunch: List<String>,
    @SerializedName("dinner") val dinner: List<String>
)

fun TodayMealResponse.toEntity(): MealEntity =
    MealEntity(
        breakfast = breakfast,
        lunch = lunch,
        dinner = dinner
    )