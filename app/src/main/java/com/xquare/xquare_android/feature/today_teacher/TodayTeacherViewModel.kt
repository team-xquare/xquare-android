package com.xquare.xquare_android.feature.today_teacher

import com.xquare.domain.entity.pick.TodaySelfStudyTeacherEntity
import com.xquare.domain.usecase.pick.TodaySelfStudyTeacherUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodayTeacherViewModel @Inject constructor(
    private val todaySelfStudyTeacherUseCase: TodaySelfStudyTeacherUseCase
): BaseViewModel<TodayTeacherViewModel.Event>(){

    fun TodaySelfStudyTeacher() = execute(
        job = { todaySelfStudyTeacherUseCase.execute(Unit)},
        onSuccess = { emitEvent(Event.Success(it)) },
        onFailure = { emitEvent(Event.Failure)}
    )



    sealed class Event{

        data class Success(val data: TodaySelfStudyTeacherEntity) : Event()

        object Failure : TodayTeacherViewModel.Event()

    }

}