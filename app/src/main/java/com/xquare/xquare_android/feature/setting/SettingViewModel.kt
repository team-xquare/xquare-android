package com.xquare.xquare_android.feature.setting

import com.xquare.domain.entity.notification.ActivateAlarmEntity
import com.xquare.domain.entity.notification.AlarmCategoriesEntity
import com.xquare.domain.usecase.notification.ActivateAlarmUseCase
import com.xquare.domain.usecase.notification.FetchAlarmCategoriesUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val activateAlarmUseCase: ActivateAlarmUseCase,
    private val fetchAlarmCategoriesUseCase: FetchAlarmCategoriesUseCase
): BaseViewModel<SettingViewModel.Event>(){

    private val _setting = MutableStateFlow(AlarmCategoriesEntity(categories = listOf()))
    val setting: StateFlow<AlarmCategoriesEntity> = _setting

    fun activateAlarm(activateAlarmEntity: ActivateAlarmEntity) =
        execute(
            job = { activateAlarmUseCase.execute(activateAlarmEntity) },
            onSuccess = { emitEvent(Event.Success) },
            onFailure = {  }
        )

    fun fetchAlarmCategories() =
        execute(
            job = { fetchAlarmCategoriesUseCase.execute(Unit) },
            onSuccess = { it.collect{ categories -> _setting.tryEmit(categories)} },
            onFailure = { }
        )

    sealed class Event{
        object Success : Event()
    }
}