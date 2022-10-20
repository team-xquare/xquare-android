package com.xquare.data.remote.response.meal

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.meal.AllMealEntity
import org.threeten.bp.LocalDate

data class AllMealResponse(
    @SerializedName("meals") val meals: List<MealWithDateResponse>
) {
    data class MealWithDateResponse(
        @SerializedName("date") val date: String,
        @SerializedName("breakfast") val breakfast: List<String>?,
        @SerializedName("lunch") val lunch: List<String>?,
        @SerializedName("dinner") val dinner: List<String>?,
    )
}

fun AllMealResponse.toEntity() =
    AllMealEntity(
        meals = meals.map { it.toEntity() }
    )

fun AllMealResponse.MealWithDateResponse.toEntity() =
    AllMealEntity.MealWithDateEntity(
        date = LocalDate.parse(date),
        breakfast = breakfast?.dropLast(1)?: listOf(),
        lunch = lunch?.dropLast(1)?: listOf(),
        dinner = dinner?.dropLast(1)?: listOf(),
        caloriesOfBreakfast = breakfast?.lastOrNull()?: "",
        caloriesOfLunch = lunch?.lastOrNull()?: "",
        caloriesOfDinner = dinner?.lastOrNull()?: ""
    )