package com.xquare.data.local.entity.point

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.xquare.domain.entity.point.PointHistoriesEntity
import javax.inject.Inject

@Entity
data class PointRoomEntity (
    @PrimaryKey(autoGenerate = true) var point: Int = 0,
    val pointHistoryEntity: PointHistoriesEntity,
)

@ProvidedTypeConverter
class PointEntityTypeConverter @Inject constructor(
    val gson: Gson
) {
    @TypeConverter
    fun fromString(value: String): PointHistoriesEntity {
        return gson.fromJson(value, PointHistoriesEntity::class.java)
    }

    @TypeConverter
    fun fromList(value: PointHistoriesEntity): String =
        gson.toJson(value)
}

fun PointRoomEntity.toEntity(pointType: Int): PointHistoriesEntity {
    return PointHistoriesEntity(
        goodPoint = this.pointHistoryEntity.goodPoint,
        badPoint = this.pointHistoryEntity.badPoint,
        pointHistories = this.pointHistoryEntity.pointHistories.filter { it.pointType == pointType }
    )
}

fun PointHistoriesEntity.toRoomEntity() =
    PointRoomEntity(
        pointHistoryEntity = this,
    )
