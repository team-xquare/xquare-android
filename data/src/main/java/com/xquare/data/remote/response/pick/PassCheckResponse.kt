package com.xquare.data.remote.response.pick

import com.xquare.domain.entity.pick.PassCheckEntity
import java.util.UUID

data class PassCheckResponse(
    val user_id: UUID,
    val name: String,
    val end_time: String,
)

fun PassCheckResponse.toEntity() =
    PassCheckEntity(
        user_id = user_id.toString(),
        name = name,
        end_time = end_time
    )

