package com.semicolon.domain.usecase.meal

import com.semicolon.domain.entity.meal.MealEntity
import com.semicolon.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTodayMealUseCase @Inject constructor(

) : UseCase<Unit, Flow<MealEntity>>() {

    override suspend fun execute(data: Unit): Flow<MealEntity> {
        TODO("Not yet implemented")
    }
}