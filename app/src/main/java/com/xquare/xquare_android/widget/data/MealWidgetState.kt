package com.xquare.xquare_android.widget.data

import java.time.LocalDate

data class MealWidgetState(
    val mealType: MealType = MealType.Breakfast,
    val meal: String,
    val date: LocalDate,
    val calories: String
)
