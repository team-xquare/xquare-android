package com.xquare.data.local.entity.meals

import androidx.room.Entity
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(primaryKeys = ["year", "month"])
data class AllMealRoomEntity(
    val year: Int,
    val month: Int,
    val meals: List<MealWithDateRoomEntity>
) {
    data class MealWithDateRoomEntity(
        val date: String,
        val breakfast: List<String>,
        val lunch: List<String>,
        val dinner: List<String>,
        val caloriesOfBreakfast: String,
        val caloriesOfLunch: String,
        val caloriesOfDinner: String
    )
}

@ProvidedTypeConverter
class MealWithDateListTypeConverter(
    private val gson: Gson
) {
    @TypeConverter
    fun fromString(value: String): List<AllMealRoomEntity.MealWithDateRoomEntity> {
        val type = object : TypeToken<List<AllMealRoomEntity.MealWithDateRoomEntity>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromList(value: List<AllMealRoomEntity.MealWithDateRoomEntity>): String =
        gson.toJson(value)
}