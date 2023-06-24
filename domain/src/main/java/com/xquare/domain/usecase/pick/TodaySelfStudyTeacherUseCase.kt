package com.xquare.domain.usecase.pick

import com.xquare.domain.entity.pick.TodaySelfStudyTeacherEntity
import com.xquare.domain.repository.pick.PickRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodaySelfStudyTeacherUseCase @Inject constructor(
    private val pickRepository: PickRepository
) : UseCase<Unit, Flow<TodaySelfStudyTeacherEntity>>(){

    override suspend fun execute(data: Unit): Flow<TodaySelfStudyTeacherEntity> =
        pickRepository.fetchTodaySelfStudyTeacher()

}