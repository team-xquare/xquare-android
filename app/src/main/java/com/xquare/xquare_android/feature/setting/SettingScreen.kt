package com.xquare.xquare_android.feature.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.semicolon.design.Body1
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.white.white
import com.semicolon.design.color.system.red.red500
import com.xquare.domain.entity.notification.ActivateAlarmEntity
import com.xquare.domain.entity.notification.AlarmCategoriesEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.CustomSwitchButton
import com.xquare.xquare_android.component.Header
import com.xquare.xquare_android.component.modal.ConfirmModal
import com.xquare.xquare_android.navigation.AppNavigationItem
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
            when (it) {
                is SettingViewModel.Event.FetchSuccess -> {
                    alarmCategoriesEntity = it.data
                }

                is SettingViewModel.Event.Failure -> {
                    makeToast(context, "...")
                }

                is SettingViewModel.Event.SecessionSuccess -> {
                    navController.navigate(AppNavigationItem.Onboard.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    val category = alarmCategoriesEntity?.categories?.associateBy { it.topic }

    val pointState = remember { mutableStateOf(true) }
    val scheduleState = remember { mutableStateOf(true) }
    val feedState = remember { mutableStateOf(true) }

    category?.forEach { (key, value) ->
        when (key) {
            "ALL" -> pointState.value = value.isActivate
            "SCHEDULE" -> scheduleState.value = value.isActivate
            "FEED" -> feedState.value = value.isActivate
        }
    }

    val onFeedStateChange: (ActivateAlarmEntity) -> Unit by remember {
        mutableStateOf({ value: ActivateAlarmEntity -> viewModel.activateAlarm(value) })
    }
    val onPointStateChange: (ActivateAlarmEntity) -> Unit by remember {
        mutableStateOf({ value: ActivateAlarmEntity -> viewModel.activateAlarm(value) })
    }
    val onScheduleStateChange: (ActivateAlarmEntity) -> Unit by remember {
        mutableStateOf({ value: ActivateAlarmEntity -> viewModel.activateAlarm(value) })
    }
    if (alarmCategoriesEntity != null) {
        SettingContent(
            onBackPress = { navController.popBackStack() },
            feedState = feedState,
            onFeedClick = onFeedStateChange,
            pointState = pointState,
            onPointClick = onPointStateChange,
            scheduleState = scheduleState,
            onScheduleClick = onScheduleStateChange,
            viewModel = viewModel,
        )
    }
}

@Composable
fun SettingContent(
    onBackPress: () -> Unit,
    feedState: MutableState<Boolean>,
    onFeedClick: (ActivateAlarmEntity) -> Unit,
    pointState: MutableState<Boolean>,
    onPointClick: (ActivateAlarmEntity) -> Unit,
    scheduleState: MutableState<Boolean>,
    onScheduleClick: (ActivateAlarmEntity) -> Unit,
    viewModel: SettingViewModel,
) {
    Column(
        modifier = Modifier
            .background(white)
            .fillMaxSize()
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp,
            ),
    ) {
        Header(
            painter = painterResource(id = R.drawable.ic_back),
            title = stringResource(id = R.string.header_setting),
            onIconClick = onBackPress
        )
        Column {
            NotificationContent(
                feedState = feedState,
                onFeedClick = { onFeedClick(it) },
                pointState = pointState,
                onPointClick = { onPointClick(it) },
                scheduleState = scheduleState,
                onScheduleClick = { onScheduleClick(it) },
            )
            Spacer(modifier = Modifier.size(20.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(gray50)
            )

        }
        AccountContent(viewModel = viewModel)
    }
}

@Composable
fun AccountContent(viewModel: SettingViewModel) {
    var logoutDialogState by remember { mutableStateOf(false) }

    if (logoutDialogState) {
        ConfirmModal(message = "정말 탈퇴 하시겠습니까?",
            confirmText = "예",
            cancelText = "아니요",
            onConfirm = { viewModel.logout() }) {
            logoutDialogState = false
        }
    }

    Column(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Subtitle4(text = "계정", Modifier.padding(bottom = 4.dp))
        Row(modifier = Modifier
            .clickable { logoutDialogState = true }
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(gray50)
            .padding(12.dp)) {
            Row {
                Column {
                    Body1(text = "회원탈퇴", color = red500)
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "XQUARE에서 계정이 영구적으로 삭제되어요.",
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationContent(
    feedState: MutableState<Boolean>,
    onFeedClick: (ActivateAlarmEntity) -> Unit,
    pointState: MutableState<Boolean>,
    onPointClick: (ActivateAlarmEntity) -> Unit,
    scheduleState: MutableState<Boolean>,
    onScheduleClick: (ActivateAlarmEntity) -> Unit,
) {
    Column(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
    ) {
        Spacer(modifier = Modifier.size(12.dp))
        Subtitle4(text = "알림")
        Text(
            text = stringResource(id = R.string.setting_screen_text),
            color = Color.DarkGray,
            fontSize = 14.sp,
        )
        NotificationItem(topic = stringResource(id = R.string.feed_alarm),
            content = stringResource(id = R.string.feed_alarm_content),
            state = feedState.value,
            onState = {
                onFeedClick(
                    ActivateAlarmEntity(
                        isActivated = it, topic = "FEED"
                    )
                )
            })
        NotificationItem(topic = stringResource(id = R.string.point_alarm),
            content = stringResource(id = R.string.point_alarm_content),
            state = pointState.value,
            onState = {
                onPointClick(
                    ActivateAlarmEntity(
                        isActivated = it, topic = "ALL"
                    )
                )
            })
        NotificationItem(topic = stringResource(id = R.string.schedule_alarm),
            content = stringResource(id = R.string.schedule_alarm_content),
            state = scheduleState.value,
            onState = {
                onScheduleClick(
                    ActivateAlarmEntity(
                        isActivated = it, topic = "SCHEDULE"
                    )
                )
            })
    }
}

@Composable
fun NotificationItem(
    topic: String,
    content: String,
    state: Boolean,
    onState: (Boolean) -> Unit,
) {
    Spacer(modifier = Modifier.size(10.dp))
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(gray50)
            .padding(10.dp)
    ) {
        Row {
            Column {
                Body1(text = topic)
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = content,
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                )
            }
            CustomSwitchButton(
                switchPadding = 2.dp,
                buttonWidth = 48.dp,
                buttonHeight = 26.dp,
                value = state,
                state = onState,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSwitch() {
    NotificationItem(
        topic = "알림",
        content = "벌점 1점",
        state = false,
        onState = {},
    )
}