package com.xquare.data.repository.meal

import com.xquare.data.fetchDataWithOfflineCache
import com.xquare.data.local.datasource.MealLocalDataSource
import com.xquare.data.remote.datasource.meal.MealRemoteDataSource
import com.xquare.data.today
import com.xquare.domain.entity.meal.AllMealEntity
import com.xquare.domain.entity.meal.MealEntity
import com.xquare.domain.repository.meal.MealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealRemoteDataSource: MealRemoteDataSource,
    private val mealLocalDataSource: MealLocalDataSource
) : MealRepository {

    override suspend fun fetchTodayMeal(): Flow<MealEntity> {
        val today = today()
        return fetchDataWithOfflineCache(
            fetchLocalData = { mealLocalDataSource.fetchMeal(today) },
            fetchRemoteData = { mealRemoteDataSource.fetchTodayMeal(today) },
            refreshLocalData = { mealLocalDataSource.saveMeal(today, it) },
            offlineOnly = true
        )
    }

    override suspend fun fetchAllMeal(): Flow<AllMealEntity> {
        val date = today()
        val year = date.year
        val month = date.monthValue
        return fetchDataWithOfflineCache(
            fetchLocalData = { mealLocalDataSource.fetchAllMeal(year, month) },
            fetchRemoteData = { mealRemoteDataSource.fetchAllMeal(year, month) },
            refreshLocalData = { mealLocalDataSource.saveAllMeal(year, month, it) },
            offlineOnly = false
        )
    }
}