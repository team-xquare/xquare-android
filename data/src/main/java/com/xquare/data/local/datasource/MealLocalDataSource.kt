package com.xquare.data.local.datasource

import com.xquare.domain.entity.meal.AllMealEntity
import com.xquare.domain.entity.meal.MealEntity
import org.threeten.bp.LocalDate

interface MealLocalDataSource {

    suspend fun fetchMeal(date: LocalDate): MealEntity

    suspend fun saveMeal(date: LocalDate, mealEntity: MealEntity)

    suspend fun fetchAllMeal(year: Int, month: Int): AllMealEntity

    suspend fun saveAllMeal(year: Int, month: Int, allMealEntity: AllMealEntity)
}