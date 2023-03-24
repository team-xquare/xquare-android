package com.xquare.xquare_android.widget.data

import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("breakfast") val breakfast: List<String>?,
    @SerializedName("lunch") val lunch: List<String>?,
    @SerializedName("dinner") val dinner: List<String>?,
    @SerializedName("breakfast_kcal") val caloriesOfBreakfast: String?,
    @SerializedName("lunch_kcal") val caloriesOfLunch: String?,
    @SerializedName("dinner_kcal") val caloriesOfDinner: String?,
)
