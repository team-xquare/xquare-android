package com.xquare.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.xquare.data.local.entity.alarm.AlarmRoomEntity

@Dao
interface AlarmDao {

    @Query("SELECT * FROM AlarmRoomEntity")
    suspend fun fetchAlarm(): AlarmRoomEntity

    @Query("DELETE FROM AlarmRoomEntity")
    suspend fun deleteAlarm()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmRoomEntity: AlarmRoomEntity)

    @Transaction
    suspend fun updateAlarm(repo: AlarmRoomEntity){
        deleteAlarm()
        insertAlarm(repo)
    }
}