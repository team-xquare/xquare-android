package com.xquare.data.remote.api

import com.xquare.data.remote.response.meal.TodayMealResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MealApi {

    @GET("meal/{date}")
    suspend fun fetchTodayMeal(
        @Path("date") date: String
    ): TodayMealResponse
}