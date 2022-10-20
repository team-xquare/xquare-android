package com.xquare.data.local.entity.meals

import androidx.room.Entity
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.xquare.domain.entity.meal.AllMealEntity
import javax.inject.Inject

@Entity(primaryKeys = ["year", "month"])
data class AllMealRoomEntity(
    val year: Int,
    val month: Int,
    val meals: AllMealEntity
)

@ProvidedTypeConverter
class AllMealEntityTypeConverter @Inject constructor(
    private val gson: Gson
) {
    @TypeConverter
    fun fromString(value: String): AllMealEntity {
        return gson.fromJson(value, AllMealEntity::class.java)
    }

    @TypeConverter
    fun fromList(value: AllMealEntity): String =
        gson.toJson(value)
}

fun AllMealRoomEntity.toEntity() = meals

fun AllMealEntity.toRoomEntity(year: Int, month: Int) =
    AllMealRoomEntity(
        year = year,
        month = month,
        meals = this
    )