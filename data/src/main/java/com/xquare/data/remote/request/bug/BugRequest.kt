package com.xquare.data.remote.request.bug

import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.bug.BugEntity

data class BugRequest(
    @SerializedName("reason") val reason: String,
    @SerializedName("category") val category: String,
    @SerializedName("images_urls") val image_urls: ArrayList<String>
)

fun BugEntity.toRequest() =
    BugRequest(
        reason = reason,
        category = category,
        image_urls = image_urls
    )