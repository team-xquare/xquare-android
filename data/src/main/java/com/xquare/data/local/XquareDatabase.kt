package com.xquare.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.xquare.data.dao.MealDao
import com.xquare.data.local.entity.meals.AllMealRoomEntity
import com.xquare.data.local.entity.meals.MealWithDateListTypeConverter

@Database(
    entities = [
        AllMealRoomEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        MealWithDateListTypeConverter::class
    ]
)
abstract class XquareDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}