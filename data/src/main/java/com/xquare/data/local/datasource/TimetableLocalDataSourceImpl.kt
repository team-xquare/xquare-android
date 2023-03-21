package com.xquare.data.local.datasource

import com.xquare.data.dao.TimetableDao
import com.xquare.data.local.entity.timetable.TimetableRoomEntity
import com.xquare.data.local.entity.timetable.toEntity
import com.xquare.data.local.entity.timetable.toRoomEntity
import com.xquare.domain.entity.timetables.TimetableEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TimetableLocalDataSourceImpl @Inject constructor(
    private val timetableDao: TimetableDao
): TimetableLocalDataSource{

    override suspend fun fetchTimetable(): TimetableEntity =
        timetableDao.fetchTimetable().toEntity()

    override suspend fun saveTimetable(timetableData: TimetableEntity) =
        timetableDao.updateTimetable(timetableData.toRoomEntity())
    }