package com.xquare.xquare_android.feature.allmeal

import com.xquare.domain.entity.meal.AllMealEntity
import com.xquare.domain.usecase.meal.FetchAllMealUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AllMealViewModel @Inject constructor(
    private val fetchAllMealUseCase: FetchAllMealUseCase
) : BaseViewModel<AllMealViewModel.Event>() {

    private val _allMeal = MutableStateFlow(AllMealEntity(listOf()))
    val allMeal: StateFlow<AllMealEntity> = _allMeal

    fun fetchAllMeal() =
        execute(
            job = { fetchAllMealUseCase.execute(Unit) },
            onSuccess = { it.collect { allMeal -> _allMeal.tryEmit(allMeal) } },
            onFailure = { emitEvent(Event.Failure) }
        )

    sealed class Event {
        data class Success(val data: AllMealEntity) : Event()
        object Failure : Event()
    }
}