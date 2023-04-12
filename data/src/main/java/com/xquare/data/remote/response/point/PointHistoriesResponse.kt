package com.xquare.data.remote.response.point

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.point.PointHistoriesEntity
import org.threeten.bp.LocalDate

data class PointHistoriesResponse(
    @SerializedName("good_point") val goodPoint: Int,
    @SerializedName("bad_point") val badPoint: Int,
    @SerializedName("point_histories") val pointHistories: List<PointHistoryResponse>,
)

data class PointHistoryResponse(
    @SerializedName("id") val id: String,
    @SerializedName("date") val date: String,
    @SerializedName("reason") val reason: String,
    @SerializedName("point_type") val pointType: Boolean,
    @SerializedName("point") val point: Int,
)

fun PointHistoriesResponse.toEntity() =
    PointHistoriesEntity(
        goodPoint = goodPoint,
        badPoint = badPoint,
        pointHistories = pointHistories.map { it.toEntity() }
    )

fun PointHistoryResponse.toEntity() =
    PointHistoriesEntity.PointHistoryEntity(
        id = id,
        date = LocalDate.parse(date),
        reason = reason,
        pointType = if (pointType) 0 else 1,
        point = point
    )