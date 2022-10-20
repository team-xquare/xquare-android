package com.xquare.data.remote.datasource

import com.xquare.data.remote.api.MealApi
import com.xquare.data.remote.response.meal.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.meal.AllMealEntity
import com.xquare.domain.entity.meal.MealEntity
import org.threeten.bp.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class MealRemoteDataSourceImpl @Inject constructor(
    private val mealApi: MealApi
) : MealRemoteDataSource {

    override suspend fun fetchTodayMeal(date: LocalDate): MealEntity =
        sendHttpRequest(
            httpRequest = { mealApi.fetchTodayMeal(date.toString()).toEntity() }
        )


    override suspend fun fetchAllMeal(year: Int, month: Int): AllMealEntity =
        sendHttpRequest(
            httpRequest = { mealApi.fetchAllMeal(year, month).toEntity() }
        )
}