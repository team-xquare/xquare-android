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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.xquare.xquare_android.util.makeToast

@Composable
fun SettingScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    val viewModel: SettingViewModel = hiltViewModel()
    var alarmCategoriesEntity: AlarmCategoriesEntity? by remember { mutableStateOf(null) }


    LaunchedEffect(Unit) {
        viewModel.fetchAlarmCategories()
        viewModel.eventFlow.collect {
            when(it) {
                is SettingViewModel.Event.FetchSuccess -> {
                    alarmCategoriesEntity = it.data
                }
                is SettingViewModel.Event.Failure -> {
                    makeToast(context,"...")
                }
            }
        }
    }

    val category = alarmCategoriesEntity?.categories?.associateBy { it.topic }

    val pointState = remember { mutableStateOf(true) }
    val scheduleState = remember { mutableStateOf(true) }
    val applicationState = remember { mutableStateOf(true) }
    val feedState = remember { mutableStateOf(true) }

    category?.forEach { (key, value) ->
        when (key) {
            "ALL" -> pointState.value = value.isActivate
            "SCHEDULE" -> scheduleState.value = value.isActivate
            "APPLICATION" -> applicationState.value = value.isActivate
            "FEED" -> feedState.value = value.isActivate
        }
    }

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
    if (alarmCategoriesEntity != null) {
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
}

@Composable
fun SettingContent(
    onBackPress: () -> Unit,
    feedState: MutableState<Boolean>,
    onFeedClick: (ActivateAlarmEntity) -> Unit,
    applicationState: MutableState<Boolean>,
    onApplicationClick: (ActivateAlarmEntity) -> Unit,
    pointState: MutableState<Boolean>,
    onPointClick: (ActivateAlarmEntity) -> Unit,
    scheduleState: MutableState<Boolean>,
    onScheduleClick: (ActivateAlarmEntity) -> Unit,
) {
    Log.d("schedule",scheduleState.value.toString())
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
            state = feedState.value,
            onState = { onFeedClick(ActivateAlarmEntity(isActivated = it, topic = "FEED")) }
        )
        SettingItem(
            topic = stringResource(id = R.string.apply_alarm),
            content = stringResource(id = R.string.apply_alarm_content),
            state = applicationState.value,
            onState = { onApplicationClick(ActivateAlarmEntity(isActivated = it, topic = "APPLICATION")) }
        )

        SettingItem(
            topic = stringResource(id = R.string.point_alarm),
            content = stringResource(id = R.string.point_alarm_content),
            state = pointState.value,
            onState = { onPointClick(ActivateAlarmEntity(isActivated = it, topic = "ALL")) }
        )
        SettingItem(topic = stringResource(id = R.string.schedule_alarm),
            content = stringResource(id = R.string.schedule_alarm_content),
            state = scheduleState.value.toString().toBoolean(),
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