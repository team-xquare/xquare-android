package com.xquare.xquare_android.feature.allmeal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.gray.gray700
import com.semicolon.design.color.primary.gray.gray800
import com.semicolon.design.color.primary.gray.gray900
import com.xquare.domain.entity.meal.AllMealEntity
import com.xquare.xquare_android.util.toKorean

@Composable
fun MealDetail(
    mealWithDateEntity: AllMealEntity.MealWithDateEntity
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = gray50, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        val date = mealWithDateEntity.date
        val month = date.monthValue
        val day = date.dayOfMonth
        val dayOfTheWeek = date.dayOfWeek.toKorean()
        val breakfast = mealWithDateEntity.breakfast.joinToString(", ")
        val lunch = mealWithDateEntity.lunch.joinToString(", ")
        val dinner = mealWithDateEntity.dinner.joinToString(", ")
        Body1(
            text = "${month}월 ${day}일 (${dayOfTheWeek})",
            color = gray900,
            fontWeight = FontWeight.Medium
        )
        if (breakfast.isNotEmpty()) {
            Spacer(Modifier.size(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Body1(text = "아침", color = gray800)
                Spacer(Modifier.size(8.dp))
                Body2(text = mealWithDateEntity.caloriesOfBreakfast, color = gray700)
            }
            Spacer(Modifier.size(4.dp))
            Body2(text = breakfast, color = gray900)
        }
        if (lunch.isNotEmpty()) {
            Spacer(Modifier.size(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Body1(text = "점심", color = gray800)
                Spacer(Modifier.size(8.dp))
                Body2(text = mealWithDateEntity.caloriesOfLunch, color = gray700)
            }
            Spacer(Modifier.size(4.dp))
            Body2(text = lunch, color = gray900)
        }
        if (dinner.isNotEmpty()) {
            Spacer(Modifier.size(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Body1(text = "저녁", color = gray800)
                Spacer(Modifier.size(8.dp))
                Body2(text = mealWithDateEntity.caloriesOfDinner, color = gray700)
            }
            Spacer(Modifier.size(4.dp))
            Body2(text = dinner, color = gray900)
        }
    }
}
