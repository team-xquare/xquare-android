package com.semicolon.xquare_android.feature.home

import com.semicolon.domain.entity.meal.MealEntity
import com.semicolon.domain.entity.point.DormitoryPointEntity
import com.semicolon.domain.usecase.meal.FetchTodayMealUseCase
import com.semicolon.domain.usecase.point.FetchDormitoryPointUseCase
import com.semicolon.domain.usecase.user.FetchUserNameUseCase
import com.semicolon.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchDormitoryPointUseCase: FetchDormitoryPointUseCase,
    private val fetchTodayMealUseCase: FetchTodayMealUseCase,
    private val fetchUserNameUseCase: FetchUserNameUseCase
) : BaseViewModel<HomeViewModel.Event>() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _dormitoryPoint = MutableStateFlow(DormitoryPointEntity(0, 0))
    val dormitoryPoint: StateFlow<DormitoryPointEntity> = _dormitoryPoint

    private val _todayMeal = MutableStateFlow(MealEntity(emptyList(), emptyList(), emptyList()))
    val todayMeal: StateFlow<MealEntity> = _todayMeal

    fun fetchUserName() {
        execute(
            job = {
                fetchUserNameUseCase.execute(Unit)
            },
            onSuccess = {
                it.collect { name ->
                    _userName.tryEmit(name)
                }
            },
            onFailure = {
                
            }
        )
    }

    fun fetchDormitoryPoint() {
        execute(
            job = {
                fetchDormitoryPointUseCase.execute(Unit)
            },
            onSuccess = {
                it.collect { point ->
                    _dormitoryPoint.tryEmit(point)
                }
            },
            onFailure = {}
        )
    }

    fun fetchTodayMeal() {
        execute(
            job = {
                fetchTodayMealUseCase.execute(Unit)
            },
            onSuccess = {
                it.collect { meal ->
                    _todayMeal.tryEmit(meal)
                }
            },
            onFailure = {

            }
        )
    }

    sealed class Event {

    }
}