package com.xquare.xquare_android.feature.home

import com.xquare.domain.entity.ClassPositionEntity
import com.xquare.domain.entity.meal.MealEntity
import com.xquare.domain.entity.user.HomeUserEntity
import com.xquare.domain.usecase.meal.FetchTodayMealUseCase
import com.xquare.domain.usecase.point.FetchDormitoryPointUseCase
import com.xquare.domain.usecase.user.FetchUserSimpleDataUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchTodayMealUseCase: FetchTodayMealUseCase,
    private val fetchUserSimpleDataUseCase: FetchUserSimpleDataUseCase,
) : BaseViewModel<HomeViewModel.Event>() {

    private val _userSimpleData = MutableStateFlow(HomeUserEntity("", "", 0, 0))
    val userSimpleData: StateFlow<HomeUserEntity> = _userSimpleData

    private val _todayMeal = MutableStateFlow(
        MealEntity(
            emptyList(),
            emptyList(),
            emptyList(),
            "", "", ""
        ))
    val todayMeal: StateFlow<MealEntity> = _todayMeal

    fun fetchUserSimpleData() {
        execute(
            job = { fetchUserSimpleDataUseCase.execute(Unit) },
            onSuccess = { _userSimpleData.tryEmit(it) },
            onFailure = {  }
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

    private val _clasPosition = MutableStateFlow(ClassPositionEntity("수준호","세미나실2-1"))
    val classPosition: StateFlow<ClassPositionEntity> = _clasPosition

    private val passCheck = MutableStateFlow()

    sealed class Event {

    }
}