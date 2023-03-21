package com.xquare.data.dao

import androidx.room.*
import com.xquare.data.local.entity.timetable.TimetableRoomEntity

@Dao
interface TimetableDao {

    @Query("SELECT * FROM TimetableRoomEntity WHERE timetableId = 0")
    suspend fun fetchTimetable(): TimetableRoomEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimetable(timetableRoomEntity: TimetableRoomEntity)

    @Transaction
    suspend fun updateTimetable(repo: TimetableRoomEntity){
        insertTimetable(repo)
    }
}