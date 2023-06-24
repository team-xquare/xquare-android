package com.xquare.xquare_android.feature.today_teacher

import com.xquare.domain.entity.pick.TodaySelfStudyTeacherEntity
import com.xquare.domain.usecase.pick.TodaySelfStudyTeacherUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TodayTeacherViewModel @Inject constructor(
    private val todaySelfStudyTeacherUseCase: TodaySelfStudyTeacherUseCase
): BaseViewModel<TodayTeacherViewModel.Event>(){

    private val _todayTeacher = MutableStateFlow(TodaySelfStudyTeacherEntity(listOf()))
    val todayTeacher: StateFlow<TodaySelfStudyTeacherEntity> = _todayTeacher

    fun todaySelfStudyTeacher() = execute(
        job = { todaySelfStudyTeacherUseCase.execute(Unit)},
        onSuccess = { it.collect{ todayTeacher -> _todayTeacher.tryEmit(todayTeacher) } },
        onFailure = {  }
    )



    sealed class Event{

        data class Success(val data: TodaySelfStudyTeacherEntity) : Event()

    }

}