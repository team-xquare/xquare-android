package com.xquare.data.dao

import android.util.Log
import androidx.room.*
import com.xquare.data.local.entity.timetable.TimetableRoomEntity

@Dao
interface TimetableDao {

    @Query("SELECT * FROM TimetableRoomEntity")
    suspend fun fetchTimetable(): TimetableRoomEntity

    @Query("DELETE FROM TimetableRoomEntity")
    suspend fun deleteTimetable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimetable(timetableRoomEntity: TimetableRoomEntity)

    @Transaction
    suspend fun updateTimetable(repo: TimetableRoomEntity){
        deleteTimetable()
        insertTimetable(repo)
    }
}