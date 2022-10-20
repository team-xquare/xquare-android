package com.xquare.data.local.datasource

import com.xquare.data.dao.MealDao
import com.xquare.data.local.entity.meals.toEntity
import com.xquare.data.local.entity.meals.toRoomEntity
import com.xquare.domain.entity.meal.AllMealEntity
import javax.inject.Inject

class MealLocalDataSourceImpl @Inject constructor(
    private val mealDao: MealDao
) : MealLocalDataSource {

    override suspend fun fetchAllMeal(year: Int, month: Int) =
        mealDao.fetchAllMeal(year, month).toEntity()

    override suspend fun saveAllMeal(year: Int, month: Int, allMealEntity: AllMealEntity) {
        mealDao.insertAllMeal(allMealEntity.toRoomEntity(year, month))
    }
}