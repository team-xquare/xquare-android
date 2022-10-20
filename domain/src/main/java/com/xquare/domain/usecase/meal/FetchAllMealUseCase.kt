package com.xquare.domain.usecase.meal

import com.xquare.domain.entity.meal.AllMealEntity
import com.xquare.domain.repository.meal.MealRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow

class FetchAllMealUseCase(
    private val mealRepository: MealRepository
) : UseCase<Unit, Flow<AllMealEntity>>() {

    override suspend fun execute(data: Unit): Flow<AllMealEntity> =
        mealRepository.fetchAllMeal()
}