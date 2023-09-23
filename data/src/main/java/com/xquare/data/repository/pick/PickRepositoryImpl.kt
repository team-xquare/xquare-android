package com.xquare.data.repository.pick

import com.xquare.data.remote.datasource.pick.PickRemoteDataSource
import com.xquare.data.today
import com.xquare.domain.entity.pick.ClassPositionEntity
import com.xquare.domain.entity.pick.PassDataEntity
import com.xquare.domain.entity.pick.PassTimeEntity
import com.xquare.domain.entity.pick.TodaySelfStudyTeacherEntity
import com.xquare.domain.repository.pick.PickRepository
import javax.inject.Inject

class PickRepositoryImpl @Inject constructor(
    private val pickRemoteDataSource: PickRemoteDataSource,
): PickRepository {
    override suspend fun fetchPassTime(): PassTimeEntity =
        pickRemoteDataSource.fetchPassTime()

    override suspend fun fetchPassData(): PassDataEntity =
        pickRemoteDataSource.fetchPassData()

    override suspend fun backToClassRoom() {
        pickRemoteDataSource.backToClassRoom()
    }

    override suspend fun fetchClassPosition(): ClassPositionEntity =
        pickRemoteDataSource.fetchClassPosition()

    val date = today()
    val month = date.monthValue
    override suspend fun fetchTodaySelfStudyTeacher(): TodaySelfStudyTeacherEntity =
        pickRemoteDataSource.fetchTodaySelfStudyTeacher(month=month.toString())
}
