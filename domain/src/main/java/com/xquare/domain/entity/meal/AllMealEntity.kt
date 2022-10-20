package com.xquare.domain.entity.meal

import org.threeten.bp.LocalDateTime

data class AllMealEntity(
    val meals: List<MealWithDateEntity>
) {
    data class MealWithDateEntity(
        val date: LocalDateTime,
        val breakfast: List<String>,
        val lunch: List<String>,
        val dinner: List<String>,
        val caloriesOfBreakfast: String,
        val caloriesOfLunch: String,
        val caloriesOfDinner: String
    )
}