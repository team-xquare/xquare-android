package com.xquare.xquare_android.feature.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.semicolon.design.Body3
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.gray.gray200
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.util.DevicePaddings

@Composable
fun AlarmScreen(
    navController: NavController,
) {
    Alarm(
        onBackPress = { navController.popBackStack() }
    )
}

@Composable
fun Alarm(
    onBackPress: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp
            ),
    ) {
        AppBar(
            painter = painterResource(R.drawable.ic_placeholder),
            text = "알림",
            onIconClick = onBackPress,
        )

        AlarmItem(
            title = "주말급식 신청",
            content = "주말급식 신청은 이번주까지kkknnjlkkk",
            sentAt = "1시간 전",
            isRead = false,
            iconPainter = ColorPainter(gray200),
        )
    }
}

@Composable
fun AlarmItem(
    title: String,
    content: String,
    sentAt: String,
    isRead: Boolean,
    iconPainter: Painter,
) {
    val backgroundColor = if (isRead) white else gray50

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor),
    ) {
        Spacer(modifier = Modifier.height(19.dp))
        Row(
            modifier = Modifier
                .padding(end = 16.dp)
        ) {
            Spacer(modifier = Modifier.size(19.dp))
            Icon(
                painter = iconPainter,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Body3(text = title, color = Color(0xFF616161))
            Body3(
                text = sentAt,
                color = Color(0xFF616161),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier.padding(start = 44.dp, end = 16.dp)
        ) {
            Subtitle4(
                text = content,
                color = Color(0xFF212121),
                lineHeight = 20,
            )
        }

        Spacer(modifier = Modifier.height(17.dp))
    }
}
