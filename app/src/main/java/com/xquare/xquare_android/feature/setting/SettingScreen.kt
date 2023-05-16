package com.xquare.xquare_android.feature.setting

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.CustomSwitchButton
import com.xquare.xquare_android.component.Header
import com.xquare.xquare_android.util.DevicePaddings

@Composable
fun SettingScreen(
    navController: NavController,
) {
    var feedState by remember { mutableStateOf(true) }
    var applicationState by remember { mutableStateOf(true) }
    var pointState by remember { mutableStateOf(true) }
    var scheduleState by remember { mutableStateOf(true) }

    val onFeedStateChange: (Boolean) -> Unit by remember {
        mutableStateOf(
            { value: Boolean -> feedState = value }
        )
    }

    val onApplicationStateChange: (Boolean) -> Unit by remember {
        mutableStateOf(
            { value: Boolean -> applicationState = value }
        )
    }

    val onPointStateChange: (Boolean) -> Unit by remember {
        mutableStateOf(
            { value: Boolean -> pointState = value }
        )
    }

    val onScheduleStateChange: (Boolean) -> Unit by remember {
        mutableStateOf(
            { value: Boolean -> scheduleState = value }
        )
    }

    Setting(
        onBackPress = { navController.popBackStack() },
        onFeedClick = onFeedStateChange,
        onApplicationClick = onApplicationStateChange,
        onPointClick = onPointStateChange,
        onScheduleClick = onScheduleStateChange
    )
}

@Composable
fun Setting(
    onBackPress: () -> Unit,
    onFeedClick: (Boolean) -> Unit,
    onApplicationClick: (Boolean) -> Unit,
    onPointClick: (Boolean) -> Unit,
    onScheduleClick: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(white)
            .fillMaxSize()
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp,
                end = 4.dp
            ),
    ) {
        Header(
            painter = painterResource(id = R.drawable.ic_back),
            title = "설정",
            onIconClick = onBackPress
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "선택한 알림을 보내드릴게요.\n" +
                    "공지 사항 알림은 꺼도 받을수 있어요",
            color = Color.LightGray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 12.dp)
        )
        Spacer(modifier = Modifier.size(44.dp))
        SettingContent(
            topic = stringResource(id = R.string.feed_alarm),
            content = stringResource(id = R.string.feed_alarm_content),
            state = { onFeedClick(it) }
        )
        SettingContent(
            topic = stringResource(id = R.string.apply_alarm),
            content = stringResource(id = R.string.apply_alarm_content),
            state = { onApplicationClick(it) }
        )
        SettingContent(
            topic = stringResource(id = R.string.point_alarm),
            content = stringResource(id = R.string.point_alarm_content),
            state = { onPointClick(it) }
        )
        SettingContent(topic = stringResource(id = R.string.schedule_alarm),
            content = stringResource(id = R.string.schedule_alarm_content),
            state = { onScheduleClick(it) }
        )
    }
}

@Composable
fun SettingContent(
    topic: String,
    content: String,
    state: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(
                start = 12.dp,
                end = 12.dp,
            )
    ) {
        Row {
            Column {
                Subtitle4(text = topic)
                Text(text = content, color = Color.Gray)
            }
            CustomSwitchButton(
                switchPadding = 2.dp,
                buttonWidth = 52.dp,
                buttonHeight = 26.dp,
                value = true,
                state = state,
            )
        }
    }
    Spacer(
        modifier = Modifier.size(
            size = 26.dp,
        ),
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewSwitch() {
    SettingContent(
        topic = "알림",
        content = "신기한 알림",
        state = {}
    )
}