package com.xquare.xquare_android.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.semicolon.design.Body1
import com.semicolon.design.Body3
import com.semicolon.design.color.primary.gray.gray800
import com.xquare.xquare_android.R

@Composable
fun Header(
    painter: Painter? = null,
    title: String,
    btnText: String? = null,
    btnEnabled: Boolean = false,
    backgroundColor: Color = Color.Unspecified,
    onIconClick: () -> Unit = {},
    onBtnClick: () -> Unit = {},
) {
    val btnColor = if (btnEnabled) gray800 else Color(0xFF9E9E9E)

    Box(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Body1(text = title)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 15.88.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.size(16.dp))
            painter?.let {
                Image(
                    modifier = Modifier
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            enabled = true
                        ) { onIconClick() },
                    painter = it,
                    contentDescription = null
                )
            }
            if (btnText != null && btnText != "null") {
                Body3(
                    text = btnText,
                    color = btnColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.End)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            enabled = btnEnabled,
                        ) {
                            onBtnClick()
                        },
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ShowHeader() {
    Header(
        painter = painterResource(id = R.drawable.ic_back),
        title = "일정 수정",
        btnText = "등록"
    )
}