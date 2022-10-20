package com.xquare.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xquare.data.local.entity.meals.AllMealRoomEntity
import com.xquare.data.local.entity.meals.MealRoomEntity

@Dao
interface MealDao {

    @Query("SELECT * FROM MealRoomEntity WHERE date = :date")
    suspend fun fetchMeal(date: String): MealRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealRoomEntity: MealRoomEntity)

    @Query("SELECT * FROM AllMealRoomEntity WHERE year = :year AND month = :month")
    suspend fun fetchAllMeal(year: Int, month: Int): AllMealRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMeal(allMealRoomEntity: AllMealRoomEntity)
}