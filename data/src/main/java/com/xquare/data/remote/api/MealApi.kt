package com.xquare.data.remote.api

import com.xquare.data.remote.response.meal.AllMealResponse
import com.xquare.data.remote.response.meal.TodayMealResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MealApi {

    @GET("meals/{date}")
    suspend fun fetchTodayMeal(
        @Path("date") date: String
    ): TodayMealResponse

    @GET("meals")
    suspend fun fetchAllMeal(
        @Query("year") year: Int,
        @Query("month") month: Int,
    ): AllMealResponse
}