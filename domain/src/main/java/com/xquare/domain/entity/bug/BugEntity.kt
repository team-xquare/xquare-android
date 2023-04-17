package com.xquare.domain.entity.bug

data class BugEntity(
    val reason: String,
    val category: String,
    val image_urls: ArrayList<String>,
)
