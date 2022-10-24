package com.xquare.data.remote.response.meal

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.meal.MealEntity

data class TodayMealResponse(
    @SerializedName("breakfast") val breakfast: List<String>?,
    @SerializedName("lunch") val lunch: List<String>?,
    @SerializedName("dinner") val dinner: List<String>?,
    @SerializedName("breakfast_kcal") val caloriesOfBreakfast: String?,
    @SerializedName("lunch_kcal") val caloriesOfLunch: String?,
    @SerializedName("dinner_kcal") val caloriesOfDinner: String?,
)

fun TodayMealResponse.toEntity(): MealEntity =
    MealEntity(
        breakfast = breakfast ?: listOf(),
        lunch = lunch ?: listOf(),
        dinner = dinner ?: listOf(),
        caloriesOfBreakfast = caloriesOfBreakfast ?: "",
        caloriesOfLunch = caloriesOfLunch ?: "",
        caloriesOfDinner = caloriesOfDinner ?: ""
    )