package com.xquare.domain.entity.point

import org.threeten.bp.LocalDate

data class PointHistoriesEntity(
    val goodPoint: Int,
    val badPoint: Int,
    val pointHistories: List<PointHistoryEntity>,
)

data class PointHistoryEntity(
    val id: String,
    val date: LocalDate,
    val reason: String,
    val pointType: Boolean,
    val point: Int,
)