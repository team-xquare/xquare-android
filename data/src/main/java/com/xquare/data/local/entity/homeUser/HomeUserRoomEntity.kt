package com.xquare.data.local.entity.homeUser

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.xquare.domain.entity.user.HomeUserEntity
import javax.inject.Inject

@Entity
data class HomeUserRoomEntity(
    @PrimaryKey(autoGenerate = true) var homeUserId: Int = 0,
    val homeUser: HomeUserEntity
)

@ProvidedTypeConverter
class HomeUserEntityTypeConverter @Inject constructor(
    val gson: Gson
) {
    @TypeConverter
    fun fromString(value: String): HomeUserEntity {
        return gson.fromJson(value, HomeUserEntity::class.java)
    }

    @TypeConverter
    fun fromList(value: HomeUserEntity): String =
        gson.toJson(value)
}

fun HomeUserRoomEntity.toEntity() =
    HomeUserEntity(
        goodPoint = this.homeUser.goodPoint,
        badPoint = this.homeUser.badPoint,
        name = this.homeUser.name,
        profileFileImage = this.homeUser.profileFileImage
    )

fun HomeUserEntity.toRoomEntity() =
    HomeUserRoomEntity(
        homeUser = this
    )
