package com.xquare.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.xquare.data.local.entity.point.PointRoomEntity

@Dao
interface PointDao {
    @Query("SELECT * FROM PointRoomEntity")
    suspend fun fetchPoint(): PointRoomEntity

    @Query("DELETE FROM PointRoomEntity")
    suspend fun deletePoint()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoint(pointRoomEntity: PointRoomEntity)

    @Transaction
    suspend fun updatePoint(repo: PointRoomEntity){
        deletePoint()
        insertPoint(repo)
    }
}