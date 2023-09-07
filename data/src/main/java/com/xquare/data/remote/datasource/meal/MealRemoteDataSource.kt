package com.xquare.data.remote.datasource.meal

import com.xquare.domain.entity.meal.AllMealEntity
import com.xquare.domain.entity.meal.MealEntity
import org.threeten.bp.LocalDate

interface MealRemoteDataSource {

    suspend fun fetchTodayMeal(date: LocalDate): MealEntity

    suspend fun fetchAllMeal(year: Int, month: Int): AllMealEntity
}