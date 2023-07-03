package com.xquare.xquare_android.feature.alarm

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.semicolon.design.Body3
import com.semicolon.design.color.primary.black.black
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.purple.purple300
import com.semicolon.design.color.primary.white.white
import com.xquare.domain.entity.notification.AlarmEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.Header
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast
import org.openjdk.tools.javac.parser.Tokens.Token
import org.openjdk.tools.javac.parser.Tokens.tokensKey
import java.time.temporal.ChronoUnit

@Composable
fun AlarmScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    val viewModel: AlarmViewModel = hiltViewModel()
    var alarmList: AlarmEntity?  by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        viewModel.fetchAlarmList()
        viewModel.eventFlow.collect {
            when (it) {
                is AlarmViewModel.Event.Success -> alarmList = it.data
                is AlarmViewModel.Event.Failure -> {
                    makeToast(context, "알림을 불러오는데 실패했습니다")
                }
            }
        }
    }
    Alarm(
        alarmList = alarmList,
        onBackPress = { navController.popBackStack() }
    )
}

@Composable
fun Alarm(
    alarmList: AlarmEntity?,
    onBackPress: () -> Unit,
) {
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
            title = "알림",
            onIconClick = onBackPress
        )

        if (alarmList == null) {
            Body3(
                text = "알림이 존재하지 않습니다.",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center)
            )
        } else {
            LazyColumn {
                items(alarmList.notifications.count()) {
                    AlarmItem(alarmList = alarmList.notifications[it])
                }
            }
        }
    }
}
@SuppressLint("NewApi")
@Composable
fun AlarmItem(
    alarmList: AlarmEntity.AlarmDataEntity
) {
    val today = java.time.LocalDateTime.now()
    val sendAt = java.time.LocalDateTime.parse(alarmList.send_at)

    val daysDifference = ChronoUnit.DAYS.between(sendAt.toLocalDate(), today.toLocalDate())
    val hoursDifference = ChronoUnit.HOURS.between(sendAt.toLocalTime(), today.toLocalTime())
    val minutesDifference = ChronoUnit.MINUTES.between(sendAt.toLocalTime(),today.toLocalTime())

    val time: String = when {
        daysDifference > 0 -> "${daysDifference}일 전"
        hoursDifference <= 0 ->"${minutesDifference}분 전"
        else -> "${hoursDifference}시간 전"
    }.let { if (it == "0분 전") "방금전" else it }

    val tint = if (alarmList.is_read) black else purple300

    val painter: Int = when (alarmList.topic) {
        "APPLICATION_WEEKEND_PICNIC",
        "APPLICATION_WEEKEND_PICNIC_RESERVATION" -> R.drawable.ic_apply
        "ALL_BAD_POINT","ALL_PENALTY_LEVEL"->R.drawable.ic_thums_down
        "ALL_GOOD_POINT"->R.drawable.ic_thumbs_up
        "FEED_NOTICE","FEED_NOTICE_COMMENT", "FEED_NOTICE_LIKE",
        "FEED_BAMBOO_COMMENT", "FEED_BAMBOO_LIKE" -> R.drawable.ic_feed
        "SCHEDULE_LOCAL","SCHEDULE_SOCIAL" -> R.drawable.ic_schedule
        else -> R.drawable.ic_alarm
    }
    val backgroundColor = if (alarmList.is_read) white else gray50
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
                painter = painterResource(id = painter),
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Body3(text = alarmList.title, color = Color(0xFF616161))
            Body3(
                text = time,
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
            Text(
                text = alarmList.content,
                color = Color(0xFF212121),
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(17.dp))
    }
}
@Composable
@Preview
fun AlarmItemPreview(){
    AlarmItem(
        alarmList = AlarmEntity.AlarmDataEntity(
            "a","a","a","20",
            false,"a","a"
        )
    )
}