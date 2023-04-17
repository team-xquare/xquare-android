package com.xquare.domain.entity.pick

import java.time.LocalDate

data class TodaySelfStudyTeacherEntity(
    val teacherList: List<TeacherEntity>
){

    data class TeacherEntity(
        val type: String,
        val date: LocalDate,
        val teacher: List<String>
    )
}