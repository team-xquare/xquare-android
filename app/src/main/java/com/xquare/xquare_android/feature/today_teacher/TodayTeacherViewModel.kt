package com.xquare.xquare_android.feature.today_teacher

import com.xquare.domain.entity.pick.TodaySelfStudyTeacherEntity
import com.xquare.domain.exception.BadRequestException
import com.xquare.domain.exception.ForbiddenException
import com.xquare.domain.exception.NotFoundException
import com.xquare.domain.exception.UnauthorizedException
import com.xquare.domain.usecase.pick.TodaySelfStudyTeacherUseCase
import com.xquare.xquare_android.base.BaseViewModel
import com.xquare.xquare_android.feature.allmeal.AllMealViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodayTeacherViewModel @Inject constructor(
    private val todaySelfStudyTeacherUseCase: TodaySelfStudyTeacherUseCase
): BaseViewModel<TodayTeacherViewModel.Event>(){

    fun TodaySelfStudyTeacher() = execute(
        job = { todaySelfStudyTeacherUseCase.execute(Unit)},
        onSuccess = {it.collect { todayTeacher -> emitEvent(Event.Success(todayTeacher)) } },
        onFailure = { emitEvent(Event.Failure)}
    )



    sealed class Event{

        data class Success(val data: TodaySelfStudyTeacherEntity) : Event()

        object Failure : TodayTeacherViewModel.Event()

    }

}