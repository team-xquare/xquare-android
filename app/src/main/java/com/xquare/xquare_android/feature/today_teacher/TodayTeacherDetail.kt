package com.xquare.xquare_android.feature.today_teacher

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.color.primary.gray.*
import com.semicolon.design.color.primary.purple.purple400
import com.xquare.domain.entity.pick.TodaySelfStudyTeacherEntity
import com.xquare.xquare_android.util.toKorean

@Stable
private val TodayTeacherShape = RoundedCornerShape(16.dp)

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayTeacherDetail(
    teacherEntity: TodaySelfStudyTeacherEntity.TeacherEntity,
    borderState: Boolean
){
    val borderColor = if (borderState) purple400 else gray50
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = gray50, shape = TodayTeacherShape)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        val date = teacherEntity.date
        val month = date.monthValue
        val day = date.dayOfMonth
        val week = date.dayOfWeek.toKorean()
            Body1(
            text = "${month}월 ${day}일 (${week})",
            color = gray900,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = gray50, shape = RoundedCornerShape(16.dp))
        ) {
            val teacher = teacherEntity.teacher
            Log.d("teacher",teacherEntity.teacher.toString())
            if (teacher[1].isNotEmpty()){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Body1(text = "2층  "+teacher[1] + "선생님", color = gray800)
                }
            }
            if (teacher[2].isNotEmpty()){
                Spacer(Modifier.size(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Body1(text = "3층  "+teacher[2]+"선생님", color = gray800)
                }
            }
            if (teacher[3].isNotEmpty()){
                Spacer(Modifier.size(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Body1(text = "4층  "+teacher[3] + "선생님", color = gray800)
                }
            }
        }

    }
}