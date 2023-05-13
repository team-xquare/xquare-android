package com.xquare.data.remote.response.release

import com.google.gson.annotations.SerializedName
import com.xquare.data.remote.response.schedules.SchedulesResponse
import com.xquare.domain.entity.reports.ReleaseEntity
import com.xquare.domain.entity.schedules.SchedulesEntity

data class ReleaseResponse(
    @SerializedName("release_note_list") val releaseNoteList: List<ReleaseDataResponse>
){
    data class ReleaseDataResponse(
        @SerializedName("release_note_id") val id: String,
        @SerializedName("release_version") val version: String,
        @SerializedName("feature_content") val featureContent: String,
        @SerializedName("bug_fix_content") val fixContent: String,
    )
}

fun ReleaseResponse.toEntity() = ReleaseEntity(
    releaseNoteList = releaseNoteList.map { it.toEntity() }
)

fun ReleaseResponse.ReleaseDataResponse.toEntity() =
    ReleaseEntity.ReleaseDataEntity(
        id = id,
        version = version,
        featureContent = featureContent,
        fixContent = fixContent
    )