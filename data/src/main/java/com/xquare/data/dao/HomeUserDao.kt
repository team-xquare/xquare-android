package com.xquare.data.dao

import androidx.room.*
import com.xquare.data.local.entity.homeUser.HomeUserRoomEntity

@Dao
interface HomeUserDao {
    @Query("SELECT * FROM HomeUserRoomEntity")
    suspend fun fetchHomeUser(): HomeUserRoomEntity

    @Query("DELETE FROM HomeUserRoomEntity")
    suspend fun deleteHomeUser()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeUser(homeUserRoomEntity: HomeUserRoomEntity)

    @Transaction
    suspend fun updateHomeUser(repo: HomeUserRoomEntity){
        deleteHomeUser()
        insertHomeUser(repo)
    }
}