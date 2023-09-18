package com.xquare.xquare_android.feature.release

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
import com.semicolon.design.Body2
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.gray.gray800
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.purple.purple200
import com.xquare.domain.entity.reports.ReleaseEntity


@Stable
private val MealDetailShape = RoundedCornerShape(16.dp)

@Composable
fun ReleaseDetail(
    releaseWithUpdateEntity: ReleaseEntity.ReleaseDataEntity,
    borderState: Boolean,
) {
    val borderColor = if (borderState) purple200 else gray50
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = gray50, shape = MealDetailShape)
            .border(width = 1.dp, color = borderColor, shape = MealDetailShape)
            .padding(16.dp)
    ) {

        val new = releaseWithUpdateEntity.featureContent
        val fixBug = releaseWithUpdateEntity.fixContent
        Body1(
            text = "${releaseWithUpdateEntity.version} 업데이트 🚀",
            color = gray900,
            fontWeight = FontWeight.Bold
        )
        if (new.isNotEmpty()) {
            Spacer(Modifier.size(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Body1(text = "새로운 기능", color = gray800, fontWeight = FontWeight.Bold)
            }
            Body2(
                text = releaseWithUpdateEntity.featureContent,
                modifier = Modifier.padding(2.dp)
            )
            Spacer(Modifier.size(2.dp))
        }
        if (fixBug.isNotEmpty()) {
            Spacer(Modifier.size(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Body1(text = "개선된 버그", color = gray800, fontWeight = FontWeight.Bold)
            }
            Body2(
                text = releaseWithUpdateEntity.fixContent,
                modifier = Modifier.padding(2.dp)
            )
            Spacer(Modifier.size(2.dp))
        }
    }
}


