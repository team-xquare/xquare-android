package com.xquare.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xquare.data.dao.*
import com.xquare.data.local.entity.alarm.AlarmEntityTypeConverter
import com.xquare.data.local.entity.alarm.AlarmRoomEntity
import com.xquare.data.local.entity.homeUser.HomeUserEntityTypeConverter
import com.xquare.data.local.entity.homeUser.HomeUserRoomEntity
import com.xquare.data.local.entity.meals.AllMealEntityTypeConverter
import com.xquare.data.local.entity.meals.AllMealRoomEntity
import com.xquare.data.local.entity.meals.MealEntityTypeConverter
import com.xquare.data.local.entity.meals.MealRoomEntity
import com.xquare.data.local.entity.point.*
import com.xquare.data.local.entity.timetable.TimetableEntityTypeConverter
import com.xquare.data.local.entity.timetable.TimetableRoomEntity

@Database(
    entities = [
        MealRoomEntity::class,
        AllMealRoomEntity::class,
        AlarmRoomEntity::class,
        TimetableRoomEntity::class,
        PointRoomEntity::class,
        HomeUserRoomEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    value = [
        MealEntityTypeConverter::class,
        AllMealEntityTypeConverter::class,
        AlarmEntityTypeConverter::class,
        TimetableEntityTypeConverter::class,
        PointEntityTypeConverter::class,
        HomeUserEntityTypeConverter::class
    ]
)
abstract class XquareDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun alarmDao(): AlarmDao
    abstract fun timetableDao(): TimetableDao
    abstract fun pointDao(): PointDao
    abstract fun homeUserDao(): HomeUserDao
}