package com.xquare.data.local.datasource

import com.xquare.domain.entity.meal.AllMealEntity

interface MealLocalDataSource {

    suspend fun fetchAllMeal(year: Int, month: Int): AllMealEntity

    suspend fun saveAllMeal(year: Int, month: Int, allMealEntity: AllMealEntity)
}