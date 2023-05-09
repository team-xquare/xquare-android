package com.xquare.data.remote.request.bug

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.bug.BugEntity

data class BugRequest(
    @SerializedName("reason") val reason: String,
    @SerializedName("category") val category: String,
    @SerializedName("image_urls") val image_urls: List<String>
)

fun BugEntity.toRequest() =
    BugRequest(
        reason = reason,
        image_urls = image_urls,
        category = category,
    )