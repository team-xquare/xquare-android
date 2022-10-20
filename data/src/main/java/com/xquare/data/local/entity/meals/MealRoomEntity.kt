package com.xquare.data.local.entity.meals

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.xquare.domain.entity.meal.MealEntity
import org.threeten.bp.LocalDate
import javax.inject.Inject

@Entity
data class MealRoomEntity(
    @PrimaryKey val date: String,
    val meal: MealEntity
)

@ProvidedTypeConverter
class MealEntityTypeConverter @Inject constructor(
    val gson: Gson
) {
    @TypeConverter
    fun fromString(value: String): MealEntity {
        return gson.fromJson(value, MealEntity::class.java)
    }

    @TypeConverter
    fun fromList(value: MealEntity): String =
        gson.toJson(value)
}

fun MealRoomEntity.toEntity() = meal

fun MealEntity.toRoomEntity(date: LocalDate) =
    MealRoomEntity(
        date = date.toString(),
        meal = this
    )
