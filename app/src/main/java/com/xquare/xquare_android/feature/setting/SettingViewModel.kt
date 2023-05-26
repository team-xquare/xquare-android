package com.xquare.xquare_android.feature.setting

import com.xquare.domain.entity.notification.ActivateAlarmEntity
import com.xquare.domain.entity.notification.AlarmCategoriesEntity
import com.xquare.domain.usecase.notification.ActivateAlarmUseCase
import com.xquare.domain.usecase.notification.FetchAlarmCategoriesUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val activateAlarmUseCase: ActivateAlarmUseCase,
    private val fetchAlarmCategoriesUseCase: FetchAlarmCategoriesUseCase
): BaseViewModel<SettingViewModel.Event>(){

    fun activateAlarm(activateAlarmEntity: ActivateAlarmEntity) =
        execute(
            job = { activateAlarmUseCase.execute(activateAlarmEntity) },
            onSuccess = { emitEvent(Event.Success) },
            onFailure = { emitEvent(Event.Failure) }
        )

    fun fetchAlarmCategories() =
        execute(
            job = { fetchAlarmCategoriesUseCase.execute(Unit) },
            onSuccess = { emitEvent(Event.FetchSuccess(it)) },
            onFailure = { emitEvent(Event.Failure) }
        )

    sealed class Event{
        object Success : Event()

        data class FetchSuccess(val data: AlarmCategoriesEntity) : Event()

        object Failure : Event()
    }
}