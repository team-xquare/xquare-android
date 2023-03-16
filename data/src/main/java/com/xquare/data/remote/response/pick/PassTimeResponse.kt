package com.xquare.data.remote.response.pick

import com.xquare.domain.entity.pick.PassTimeEntity
import java.util.UUID

data class PassTimeResponse(
    val user_id: UUID,
    val name: String,
    val end_time: String,
)

fun PassTimeResponse.toEntity() =
    PassTimeEntity(
        user_id = user_id.toString(),
        name = name,
        end_time = end_time.substring(0..1) + "시 "
                    + end_time.substring(3..4) + "분"
    )

