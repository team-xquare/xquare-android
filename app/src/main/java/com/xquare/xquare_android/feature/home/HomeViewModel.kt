package com.xquare.xquare_android.feature.home

import android.util.Log
import com.xquare.domain.entity.pick.ClassPositionEntity
import com.xquare.domain.entity.pick.PassTimeEntity
import com.xquare.domain.entity.meal.MealEntity
import com.xquare.domain.entity.user.HomeUserEntity
import com.xquare.domain.usecase.meal.FetchTodayMealUseCase
import com.xquare.domain.usecase.pick.BackToClassRoomUseCase
import com.xquare.domain.usecase.pick.FetchClassPositionUseCase
import com.xquare.domain.usecase.pick.FetchPassTimeUseCase
import com.xquare.domain.usecase.timetables.FetchPeriodUseCase
import com.xquare.domain.usecase.user.FetchUserSimpleDataUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchTodayMealUseCase: FetchTodayMealUseCase,
    private val fetchUserSimpleDataUseCase: FetchUserSimpleDataUseCase,
    private val fetchClassPositionUseCase: FetchClassPositionUseCase,
    private val fetchPeriodUseCase: FetchPeriodUseCase,
    private val backToClassRoomUseCase: BackToClassRoomUseCase,
    private val fetchPassTimeUseCase: FetchPassTimeUseCase,
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

    private val _classPosition = MutableStateFlow(ClassPositionEntity("",""))
    val classPosition: StateFlow<ClassPositionEntity> = _classPosition

    fun fetchClassPosition() {
        execute(
            job = { fetchClassPositionUseCase.execute(Unit) },
            onSuccess = { _classPosition.tryEmit(it) },
            onFailure = {  }
        )
    }

    fun fetchPeriod() {
        execute(
            job = { fetchPeriodUseCase.execute(Unit) },
            onSuccess = { emitEvent(Event.SuccessFetchPeriod(it)) },
            onFailure = { Log.d("TAG", "fetchPeriod Fail") }
        )
    }

    fun backToClassRoom(
        period: Int
    ) {
        execute(
            job = { backToClassRoomUseCase.execute(period) },
            onSuccess = { emitEvent(Event.BackToClassRoom) },
            onFailure = {},
        )
    }

    private val _passCheck = MutableStateFlow(PassTimeEntity("","",""))
    val passCheck: StateFlow<PassTimeEntity> = _passCheck

    fun fetchPassTime() {
        execute(
            job = { fetchPassTimeUseCase.execute(Unit) },
            onSuccess = { _passCheck.tryEmit(it) },
            onFailure = {  }
        )
    }

    sealed class Event {
        data class SuccessFetchPeriod(val period: Int) : Event()
        object BackToClassRoom : Event()
    }
}