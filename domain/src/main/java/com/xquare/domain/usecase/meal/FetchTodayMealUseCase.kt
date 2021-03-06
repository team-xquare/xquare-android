package com.xquare.domain.usecase.meal

import com.xquare.domain.entity.meal.MealEntity
import com.xquare.domain.repository.meal.MealRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTodayMealUseCase @Inject constructor(
    private val mealRepository: MealRepository
) : UseCase<Unit, Flow<MealEntity>>() {

    override suspend fun execute(data: Unit): Flow<MealEntity> =
        mealRepository.fetchTodayMeal()
}