package com.semicolon.domain.repository.meal

import com.semicolon.domain.entity.meal.MealEntity
import kotlinx.coroutines.flow.Flow

interface MealRepository {

    suspend fun fetchTodayMeal(): Flow<MealEntity>
}