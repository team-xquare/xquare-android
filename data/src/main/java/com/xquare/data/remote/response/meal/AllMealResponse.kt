package com.xquare.data.remote.response.meal

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.meal.AllMealEntity
import org.threeten.bp.LocalDateTime

data class AllMealResponse(
    @SerializedName("meals") val meals: List<MealWithDateResponse>
) {
    data class MealWithDateResponse(
        @SerializedName("date") val date: String,
        @SerializedName("breakfast") val breakfast: List<String>,
        @SerializedName("lunch") val lunch: List<String>,
        @SerializedName("dinner") val dinner: List<String>,
    )
}

fun AllMealResponse.toEntity() =
    AllMealEntity(
        meals = meals.map { it.toEntity() }
    )

fun AllMealResponse.MealWithDateResponse.toEntity() =
    AllMealEntity.MealWithDateEntity(
        date = LocalDateTime.parse(date),
        breakfast = breakfast.dropLast(1),
        lunch = lunch.dropLast(1),
        dinner = lunch.dropLast(1),
        caloriesOfBreakfast = breakfast.last(),
        caloriesOfLunch = lunch.last(),
        caloriesOfDinner = dinner.last()
    )