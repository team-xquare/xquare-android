package com.xquare.xquare_android.feature.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.purple.purple500
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.Header
import com.xquare.xquare_android.util.DevicePaddings

@Composable
fun SettingScreen(
    navController: NavController,
) {
    Setting(onBackPress = { navController.popBackStack() })
}

@Composable
fun Setting(
    onBackPress: () -> Unit,
) {
    var feedState by remember { mutableStateOf(false) }
    var applicationState by remember { mutableStateOf(false) }
    var pointState by remember { mutableStateOf(false) }
    var scheduleState by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .background(white)
            .fillMaxSize()
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp
            ),
    ) {
        Header(
            painter = painterResource(id = R.drawable.ic_back),
            title = "설정",
            onIconClick = onBackPress
        )
        Spacer(modifier = Modifier.size(28.dp))
        Text(
            text = "선택한 알림을 보내드릴게요.\n" +
                "공지사항 알림은 꺼도 받을수 있어요",
            color = Color.LightGray,
            modifier = Modifier.padding(start = 12.dp)
        )
        Spacer(modifier = Modifier.size(20.dp))
        SettingContent(
            topic = stringResource(id = R.string.feed_alarm),
            content = stringResource(id = R.string.feed_alarm_content),
            state = { feedState = it}
        )
        SettingContent(
            topic = stringResource(id = R.string.apply_alarm),
            content = stringResource(id = R.string.apply_alarm_content),
            state = { applicationState = it}
        )
        SettingContent(
            topic = stringResource(id = R.string.point_alarm),
            content = stringResource(id = R.string.point_alarm_content),
            state = {pointState = it}
        )
        SettingContent(
            topic = stringResource(id = R.string.schedule_alarm),
            content = stringResource(id = R.string.schedule_alarm_content),
            state = {scheduleState = it}
        )
    }
}

@Composable
fun SettingContent(
    topic: String,
    content: String,
    state: (Boolean) -> Unit,
){
    var switchState by remember { mutableStateOf(false) }
    Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
        Row {
            Column {
                Subtitle4(text = topic)
                Text(text = content, color = Color.Gray)
            }
            Switch(
                checked = switchState,
                onCheckedChange = {
                    switchState = it
                    state(switchState)
                  },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End),
                colors = SwitchDefaults.colors(checkedTrackColor = purple500, checkedThumbColor = white)
            )
        }
    }
    Spacer(modifier = Modifier.size(26.dp))
}

@Composable
@Preview
fun PreviewSwitch(){
    SettingContent(topic = "알림", content = "신기한 알림", state = {})
}