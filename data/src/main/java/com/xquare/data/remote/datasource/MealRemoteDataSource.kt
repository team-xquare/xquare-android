package com.xquare.data.remote.datasource

import com.xquare.domain.entity.meal.MealEntity

interface MealRemoteDataSource {

    fun fetchTodayMeal(date: String): MealEntity
}