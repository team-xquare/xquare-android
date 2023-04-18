package com.xquare.xquare_android.feature.today_teacher

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.gray.gray500
import com.semicolon.design.color.primary.gray.gray800
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.purple.purple400
import com.xquare.domain.entity.pick.TodaySelfStudyTeacherEntity

@Stable
private val TodayTeacherShape = RoundedCornerShape(16.dp)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayTeacherDetail(
    teacherEntity: TodaySelfStudyTeacherEntity.TeacherEntity,
    borderState: Boolean
){
    val borderState = if (borderState) purple400 else gray50
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = gray50, shape = TodayTeacherShape)
            .padding(16.dp)
    ) {
        val date = teacherEntity.date
        val month = date.monthValue
        val day = date.dayOfMonth
        val week = date.dayOfWeek
        val teacher = teacherEntity.teacher.joinToString("")
        Body1(
            text = "${month}월 ${day}일 (${week})",
            color = gray900,
            fontWeight = FontWeight.Medium
        )
        if (teacher.isNotEmpty()){
            Spacer(Modifier.size(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Body1(text = "2층", color = gray800)
                Spacer(Modifier.size(8.dp))
            }
            Spacer(Modifier.size(8.dp))
            Body2(text = teacher, color = gray900, fontWeight = FontWeight.Medium)

        }

    }
}