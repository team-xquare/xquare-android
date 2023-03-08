package com.xquare.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.xquare.data.dao.AlarmDao
import com.xquare.data.dao.MealDao
import com.xquare.data.local.entity.alarm.AlarmEntityTypeConverter
import com.xquare.data.local.entity.alarm.AlarmRoomEntity
import com.xquare.data.local.entity.meals.AllMealEntityTypeConverter
import com.xquare.data.local.entity.meals.AllMealRoomEntity
import com.xquare.data.local.entity.meals.MealEntityTypeConverter
import com.xquare.data.local.entity.meals.MealRoomEntity

@Database(
    entities = [
        MealRoomEntity::class,
        AllMealRoomEntity::class,
        AlarmRoomEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        MealEntityTypeConverter::class,
        AllMealEntityTypeConverter::class,
        AlarmEntityTypeConverter::class
    ]
)
abstract class XquareDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun alarmDao(): AlarmDao
}