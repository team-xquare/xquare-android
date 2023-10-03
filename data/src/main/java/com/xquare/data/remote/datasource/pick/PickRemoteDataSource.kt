package com.xquare.data.remote.datasource.pick

import com.xquare.domain.entity.pick.ClassPositionEntity
import com.xquare.domain.entity.pick.PassDataEntity
import com.xquare.domain.entity.pick.PassTimeEntity
import com.xquare.domain.entity.pick.TodaySelfStudyTeacherEntity
import kotlinx.coroutines.flow.Flow

interface PickRemoteDataSource {
    suspend fun fetchPassTime(): PassTimeEntity

    suspend fun fetchPassData(): PassDataEntity

    suspend fun backToClassRoom()

    suspend fun fetchClassPosition(): ClassPositionEntity

    suspend fun fetchTodaySelfStudyTeacher(month: String): TodaySelfStudyTeacherEntity



}