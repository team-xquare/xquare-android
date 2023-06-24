package com.xquare.xquare_android.feature.alarm

import android.util.Log
import com.xquare.domain.entity.notification.AlarmEntity
import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.usecase.notification.FetchAlarmUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val fetchAlarmUseCase: FetchAlarmUseCase,
): BaseViewModel<AlarmViewModel.Event>() {

    private val _alarmList = MutableStateFlow(AlarmEntity(listOf()))
    val alarmList: StateFlow<AlarmEntity> = _alarmList

    fun fetchAlarmList() =
        execute(
            job = { fetchAlarmUseCase.execute(Unit) },
            onSuccess = { it.collect{ alarmList -> _alarmList.tryEmit(alarmList)} },
            onFailure = {  }
        )

    sealed class Event {
        data class Success(val data: AlarmEntity) : Event()
    }
}