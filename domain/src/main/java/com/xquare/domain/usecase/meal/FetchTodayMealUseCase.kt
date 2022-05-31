package com.xquare.domain.usecase.meal

import com.xquare.domain.entity.meal.MealEntity
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTodayMealUseCase @Inject constructor(

) : UseCase<Unit, Flow<MealEntity>>() {

    override suspend fun execute(data: Unit): Flow<MealEntity> {
        TODO("Not yet implemented")
    }
}