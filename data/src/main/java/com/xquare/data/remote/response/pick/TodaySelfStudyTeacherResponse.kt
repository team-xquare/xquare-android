package com.xquare.data.remote.response.pick


import com.google.gson.annotations.SerializedName
import com.xquare.domain.entity.pick.TodaySelfStudyTeacherEntity
import org.threeten.bp.LocalDate

data class TodaySelfStudyTeacherResponse(
   @SerializedName("self_study_list") val teacherList: List<TodayTeacherWithDataResponse>,
) {

   data class TodayTeacherWithDataResponse(
      @SerializedName("type") val type: String,
      @SerializedName("date") val date: String,
      @SerializedName("teacher") val teacher: List<String>?,
   )
}
   fun TodaySelfStudyTeacherResponse.toEntity() =
      TodaySelfStudyTeacherEntity(
         teacherList = teacherList.map { it.toEntity() }
   )

   fun TodaySelfStudyTeacherResponse.TodayTeacherWithDataResponse.toEntity() =
      TodaySelfStudyTeacherEntity.TeacherEntity(
         type = type ?: "",
         date = LocalDate.parse(date),
         teacher = teacher ?: listOf()
      )




