package com.xquare.data.local.entity.alarm

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.xquare.domain.entity.notification.AlarmEntity
import javax.inject.Inject

@Entity
data class AlarmRoomEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val alarm: AlarmEntity
)

@ProvidedTypeConverter
class AlarmEntityTypeConverter @Inject constructor(
    val gson: Gson
) {
    @TypeConverter
    fun fromString(value: String): AlarmEntity {
        return gson.fromJson(value, AlarmEntity::class.java)
    }

    @TypeConverter
    fun fromList(value: AlarmEntity): String =
        gson.toJson(value)
}

fun AlarmRoomEntity.toEntity() =
    AlarmEntity(
        notifications = this.alarm.notifications
    )

fun AlarmEntity.toRoomEntity() =
    AlarmRoomEntity(
        alarm = this
    )
