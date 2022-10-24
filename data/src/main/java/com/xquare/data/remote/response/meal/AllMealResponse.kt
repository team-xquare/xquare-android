package com.xquare.data.remote.response.meal

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.meal.AllMealEntity
import org.threeten.bp.LocalDate

data class AllMealResponse(
    @SerializedName("meals") val meals: List<MealWithDateResponse>,
) {
    data class MealWithDateResponse(
        @SerializedName("date") val date: String,
        @SerializedName("breakfast") val breakfast: List<String>?,
        @SerializedName("lunch") val lunch: List<String>?,
        @SerializedName("dinner") val dinner: List<String>?,
        @SerializedName("breakfast_kcal") val caloriesOfBreakfast: String?,
        @SerializedName("lunch_kcal") val caloriesOfLunch: String?,
        @SerializedName("dinner_kcal") val caloriesOfDinner: String?,
    )
}

fun AllMealResponse.toEntity() =
    AllMealEntity(
        meals = meals.map { it.toEntity() }
    )

fun AllMealResponse.MealWithDateResponse.toEntity() =
    AllMealEntity.MealWithDateEntity(
        date = LocalDate.parse(date),
        breakfast = breakfast ?: listOf(),
        lunch = lunch ?: listOf(),
        dinner = dinner ?: listOf(),
        caloriesOfBreakfast = caloriesOfBreakfast ?: "",
        caloriesOfLunch = caloriesOfLunch ?: "",
        caloriesOfDinner = caloriesOfDinner ?: ""
    )