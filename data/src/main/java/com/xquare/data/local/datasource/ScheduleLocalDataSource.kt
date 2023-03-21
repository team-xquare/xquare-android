package com.xquare.data.local.datasource

import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.timetables.TimetableEntity

interface ScheduleLocalDataSource {

    suspend fun fetchSchedule(): SchedulesEntity

    suspend fun saveSchedule(scheduleData: SchedulesEntity)
}