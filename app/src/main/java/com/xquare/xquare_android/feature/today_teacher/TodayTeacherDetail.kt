package com.xquare.xquare_android.feature.today_teacher

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.semicolon.design.color.primary.gray.*
import com.semicolon.design.color.primary.purple.purple200
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
) {
    val borderColor = if (borderState) purple200 else gray50
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
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = gray50, shape = RoundedCornerShape(16.dp))
        ) {
            for (floor in 0..4) {
                if (teacherEntity.teacher[floor].isNotEmpty()) JunJaBoy(teacher = teacherEntity, floor = floor)
            }
        }
    }
}

@Composable
fun JunJaBoy(
    teacher: TodaySelfStudyTeacherEntity.TeacherEntity,
    floor: Int
) {
    Spacer(Modifier.size(8.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Body1(text = "${floor+1}층  " + teacher.teacher[floor] + "선생님", color = gray900)
    }
}