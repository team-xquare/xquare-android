package com.xquare.data.local.entity.timetable

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.xquare.domain.entity.timetables.TimetableEntity
import javax.inject.Inject

@Entity
data class TimetableRoomEntity (
    @PrimaryKey(autoGenerate = true) var timetableId: Int = 0,
    val timetable:TimetableEntity
)

@ProvidedTypeConverter
class TimetableEntityTypeConverter @Inject constructor(
    val gson: Gson
) {
    @TypeConverter
    fun fromString(value: String): TimetableEntity {
        return gson.fromJson(value, TimetableEntity::class.java)
    }

    @TypeConverter
    fun fromList(value: TimetableEntity): String =
        gson.toJson(value)
}

fun TimetableRoomEntity.toEntity() =
    TimetableEntity(
        week_timetable = this.timetable.week_timetable
)

fun TimetableEntity.toRoomEntity() =
    TimetableRoomEntity(
        timetable = this
    )
