package com.xquare.xquare_android.feature.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.white.white
import com.xquare.domain.entity.notification.ActivateAlarmEntity
import com.xquare.domain.entity.notification.AlarmCategoriesEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.CustomSwitchButton
import com.xquare.xquare_android.component.Header
import com.xquare.xquare_android.util.DevicePaddings

@Composable
fun SettingScreen(
    navController: NavController,
) {
    val viewModel: SettingViewModel = hiltViewModel()
    var alarmCategoriesEntity: AlarmCategoriesEntity? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchAlarmCategories()
        viewModel.eventFlow.collect { event ->
            if (event is SettingViewModel.Event.FetchSuccess) {
                alarmCategoriesEntity = event.data
            }
        }
    }

    val category = alarmCategoriesEntity?.categories?.associateBy { it.topic }

    val pointState by remember { mutableStateOf(category?.get("ALL")?.isActivate ?: true) }
    val scheduleState by remember { mutableStateOf(category?.get("SCHEDULE")?.isActivate ?: true) }
    val applicationState by remember { mutableStateOf(category?.get("APPLY")?.isActivate ?: true) }
    val feedState by remember { mutableStateOf(category?.get("FEED")?.isActivate ?: true) }

    val onFeedStateChange: (ActivateAlarmEntity) -> Unit by remember {
        mutableStateOf(
            { value: ActivateAlarmEntity -> viewModel.activateAlarm(value) }
        )
    }

    val onApplicationStateChange: (ActivateAlarmEntity) -> Unit by remember {
        mutableStateOf(
            { value: ActivateAlarmEntity -> viewModel.activateAlarm(value) }
        )
    }

    val onPointStateChange: (ActivateAlarmEntity) -> Unit by remember {
        mutableStateOf(
            { value: ActivateAlarmEntity -> viewModel.activateAlarm(value) }
        )
    }

    val onScheduleStateChange: (ActivateAlarmEntity) -> Unit by remember {
        mutableStateOf(
            { value: ActivateAlarmEntity -> viewModel.activateAlarm(value) }
        )
    }

    SettingContent(
        onBackPress = { navController.popBackStack() },
        feedState = feedState,
        onFeedClick = onFeedStateChange,
        applicationState = applicationState,
        onApplicationClick = onApplicationStateChange,
        pointState = pointState,
        onPointClick = onPointStateChange,
        scheduleState = scheduleState,
        onScheduleClick = onScheduleStateChange,
    )
}

@Composable
fun SettingContent(
    onBackPress: () -> Unit,
    feedState: Boolean,
    onFeedClick: (ActivateAlarmEntity) -> Unit,
    applicationState: Boolean,
    onApplicationClick: (ActivateAlarmEntity) -> Unit,
    pointState: Boolean,
    onPointClick: (ActivateAlarmEntity) -> Unit,
    scheduleState: Boolean,
    onScheduleClick: (ActivateAlarmEntity) -> Unit,
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
            title = stringResource(id = R.string.header_setting),
            onIconClick = onBackPress
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = stringResource(id = R.string.setting_screen_text),
            color = Color.LightGray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 12.dp)
        )
        Spacer(modifier = Modifier.size(44.dp))
        SettingItem(
            topic = stringResource(id = R.string.feed_alarm),
            content = stringResource(id = R.string.feed_alarm_content),
            state = feedState,
            onState = { onFeedClick(ActivateAlarmEntity(isActivated = it, topic = "FEED")) }
        )
        SettingItem(
            topic = stringResource(id = R.string.apply_alarm),
            content = stringResource(id = R.string.apply_alarm_content),
            state = applicationState,
            onState = { onApplicationClick(ActivateAlarmEntity(isActivated = it, topic = "APPLY")) }
        )
        SettingItem(
            topic = stringResource(id = R.string.point_alarm),
            content = stringResource(id = R.string.point_alarm_content),
            state = pointState,
            onState = { onPointClick(ActivateAlarmEntity(isActivated = it, topic = "ALL")) }
        )
        SettingItem(topic = stringResource(id = R.string.schedule_alarm),
            content = stringResource(id = R.string.schedule_alarm_content),
            state = scheduleState,
            onState = { onScheduleClick(ActivateAlarmEntity(isActivated = it, topic = "SCHEDULE")) }
        )
    }
}

@Composable
fun SettingItem(
    topic: String,
    content: String,
    state: Boolean,
    onState: (Boolean) -> Unit,
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
                value = state,
                state = onState,
            )
        }
    }
    Spacer(
        modifier = Modifier.size(
            size = 26.dp,
        )
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewSwitch() {
    SettingItem(
        topic = "알림",
        content = "벌점 1점",
        state = false,
        onState = {},
    )
}