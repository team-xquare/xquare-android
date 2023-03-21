package com.xquare.data.local.entity.schedule

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.entity.timetables.TimetableEntity
import javax.inject.Inject

@Entity
data class ScheduleRoomEntity (
    @PrimaryKey(autoGenerate = true) var scheduleId: Int = 0,
    val schedule: SchedulesEntity
)


@ProvidedTypeConverter
class ScheduleEntityTypeConverter @Inject constructor(
    val gson: Gson
) {
    @TypeConverter
    fun fromString(value: String): SchedulesEntity {
        return gson.fromJson(value, SchedulesEntity::class.java)
    }

    @TypeConverter
    fun fromList(value: SchedulesEntity): String =
        gson.toJson(value)
}

fun ScheduleRoomEntity.toEntity() = schedule

fun SchedulesEntity.toRoomEntity() =
    ScheduleRoomEntity(
        schedule = this
    )
