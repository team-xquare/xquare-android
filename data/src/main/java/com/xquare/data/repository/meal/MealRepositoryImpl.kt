package com.xquare.data.repository.meal

import com.xquare.domain.entity.meal.MealEntity
import com.xquare.domain.repository.meal.MealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(

): MealRepository {

    override suspend fun fetchTodayMeal(): Flow<MealEntity> {
        TODO("Not yet implemented")
    }
}