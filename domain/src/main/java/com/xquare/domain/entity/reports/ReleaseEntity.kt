package com.xquare.domain.entity.reports

data class ReleaseEntity(
    val releaseNoteList: List<ReleaseDataEntity>
){
    data class ReleaseDataEntity(
        val id: String,
        val version: String,
        val featureContent: String,
        val fixContent: String,
    )
}
