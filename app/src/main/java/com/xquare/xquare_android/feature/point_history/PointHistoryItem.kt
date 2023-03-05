package com.xquare.xquare_android.feature.point_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.semicolon.design.Body1
import com.semicolon.design.Body3
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.gray.gray50
import com.xquare.domain.entity.point.PointHistoryEntity
import org.threeten.bp.LocalDate

@Composable
fun PointHistoryItem(pointHistoryEntity: PointHistoryEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .background(color = gray50, shape = RoundedCornerShape(12.dp))
            .padding(start = 16.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Body1(text = pointHistoryEntity.reason)
            Body3(text = pointHistoryEntity.date.toString())
        }
        Subtitle4(text = pointHistoryEntity.point.toString())
    }
}

@Preview
@Composable
fun PointHistoryItemPreview() {
    PointHistoryItem(pointHistoryEntity =
    PointHistoryEntity(
        "",
        LocalDate.parse("2022-12-31"),
        "그냥",
        false,
        5
    )
    )
}