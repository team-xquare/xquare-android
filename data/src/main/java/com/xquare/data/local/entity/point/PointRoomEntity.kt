package com.xquare.data.local.entity.point

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.xquare.domain.entity.point.PointHistoriesEntity
import javax.inject.Inject

@Entity
data class PointRoomEntity (
    @PrimaryKey(autoGenerate = true) var badPointId: Int = 0,
    val badPoint: PointHistoriesEntity,
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

fun PointRoomEntity.toEntity() =
    PointHistoriesEntity(
        goodPoint = this.badPoint.goodPoint,
        badPoint = this.badPoint.badPoint,
        pointHistories = this.badPoint.pointHistories
    )

fun PointHistoriesEntity.toRoomEntity() =
    PointRoomEntity(
        badPoint = this,
    )
