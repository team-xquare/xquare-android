package com.xquare.data.remote.datasource

import com.xquare.domain.entity.meal.MealEntity

interface MealRemoteDataSource {

    suspend fun fetchTodayMeal(date: String): MealEntity
}