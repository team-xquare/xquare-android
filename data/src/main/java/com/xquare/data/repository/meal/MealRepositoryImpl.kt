package com.xquare.data.repository.meal

import com.xquare.data.remote.datasource.MealRemoteDataSource
import com.xquare.domain.entity.meal.MealEntity
import com.xquare.domain.repository.meal.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealRemoteDataSource: MealRemoteDataSource
) : MealRepository {

    override suspend fun fetchTodayMeal(): Flow<MealEntity> {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val today = LocalDate.now().format(dateFormatter)
        return flow { emit(mealRemoteDataSource.fetchTodayMeal(today)) }
    }
}