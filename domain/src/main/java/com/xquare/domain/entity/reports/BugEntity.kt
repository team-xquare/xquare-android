package com.xquare.domain.entity.reports

data class BugEntity(
    val reason: String,
    val category: String,
    val image_urls: List<String>,
)
