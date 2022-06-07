package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.MealApi
import com.xquare.data.remote.response.meal.toEntity
import com.xquare.domain.entity.meal.MealEntity
import javax.inject.Inject

class MealRemoteDataSourceImpl @Inject constructor(
    private val mealApi: MealApi
) : MealRemoteDataSource {

    override suspend fun fetchTodayMeal(date: String): MealEntity =
        mealApi.fetchTodayMeal(date).toEntity()
}