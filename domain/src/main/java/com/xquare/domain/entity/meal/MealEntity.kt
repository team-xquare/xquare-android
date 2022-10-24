package com.xquare.domain.entity.meal

data class MealEntity(
    val breakfast: List<String>,
    val lunch: List<String>,
    val dinner: List<String>,
    val caloriesOfBreakfast: String,
    val caloriesOfLunch: String,
    val caloriesOfDinner: String
)