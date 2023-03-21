package com.xquare.data.local.datasource

import com.xquare.data.dao.ScheduleDao
import com.xquare.data.dao.TimetableDao
import com.xquare.data.local.entity.schedule.toEntity
import com.xquare.data.local.entity.schedule.toRoomEntity
import com.xquare.data.local.entity.timetable.toEntity
import com.xquare.data.local.entity.timetable.toRoomEntity
import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.timetables.TimetableEntity
import javax.inject.Inject

class ScheduleLocalDataSourceImpl @Inject constructor(
    private val scheduleDao: ScheduleDao
): ScheduleLocalDataSource{

    override suspend fun fetchSchedule(): SchedulesEntity =
        scheduleDao.fetchSchedule().toEntity()

    override suspend fun saveSchedule(scheduleData: SchedulesEntity) =
        scheduleDao.insertSchedule(scheduleData.toRoomEntity())
}