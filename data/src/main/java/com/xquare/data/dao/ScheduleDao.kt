package com.xquare.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xquare.data.local.entity.schedule.ScheduleRoomEntity
import com.xquare.data.local.entity.timetable.TimetableRoomEntity

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM ScheduleRoomEntity WHERE scheduleId = 0")
    suspend fun fetchSchedule(): ScheduleRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(scheduleRoomEntity: ScheduleRoomEntity)
}