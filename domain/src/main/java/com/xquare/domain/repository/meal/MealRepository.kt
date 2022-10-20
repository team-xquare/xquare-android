package com.xquare.domain.repository.meal

import com.xquare.domain.entity.meal.AllMealEntity
import com.xquare.domain.entity.meal.MealEntity
import kotlinx.coroutines.flow.Flow

interface MealRepository {

    suspend fun fetchTodayMeal(): Flow<MealEntity>

    suspend fun fetchAllMeal(): Flow<AllMealEntity>
}