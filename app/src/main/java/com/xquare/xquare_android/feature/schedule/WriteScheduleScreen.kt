package com.xquare.xquare_android.feature.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xquare.domain.entity.schedules.CreateSchedulesEntity
import com.xquare.domain.entity.schedules.FixSchedulesEntity
import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.Header
import com.xquare.xquare_android.component.HighlightedText
import com.xquare.xquare_android.component.TextField
import com.xquare.xquare_android.component.TextFieldBtn
import com.xquare.xquare_android.component.modal.DatePickerModal
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WriteScheduleScreen(
    navController: NavController,
    schedulesData: SchedulesEntity.SchedulesDataEntity?
) {
    val context = LocalContext.current
    val scheduleViewModel: ScheduleViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        scheduleViewModel.eventFlow.collect {
            when (it) {
                is ScheduleViewModel.Event.CreateSuccess -> {
                    makeToast(context, "일정을 추가하였습니다")
                    navController.popBackStack()
                }
                is ScheduleViewModel.Event.FixSuccess -> {
                    makeToast(context, "일정을 수정하였습니다")
                    navController.popBackStack()
                }
                is ScheduleViewModel.Event.Failure -> makeToast(context, it.message)
                else -> {

                }
            }
        }
    }
    WriteScheduleContent(
        schedulesData = schedulesData,
        onIconClick = { navController.popBackStack() },
        onBtnClick = { name, date ->
            if (schedulesData == null) {
                scheduleViewModel.createSchedules(
                    CreateSchedulesEntity(
                        name = name,
                        date = LocalDate.parse(date)
                    )
                )
            } else {
                scheduleViewModel.fixSchedules(
                    FixSchedulesEntity(
                        id = schedulesData.id,
                        name = name,
                        date = LocalDate.parse(date)
                    )
                )
            }
        }
    )
}

@Composable
fun WriteScheduleContent(
    schedulesData: SchedulesEntity.SchedulesDataEntity?,
    onIconClick: () -> Unit,
    onBtnClick: (String, String) -> Unit,
) {
    var name by remember { mutableStateOf(schedulesData?.name ?: "") }
    var date by remember { mutableStateOf(schedulesData?.date?:"") }
    var timerModalState by remember { mutableStateOf(false) }
    val btnEnabled = name.length > 1 && date.isNotEmpty()

    Column(modifier = Modifier.padding(top = DevicePaddings.statusBarHeightDp.dp)) {
        Header(
            painter = painterResource(id = R.drawable.ic_back),
            title = "일정 작성",
            btnText = "등록",
            btnEnabled = btnEnabled,
            onIconClick = onIconClick,
            onBtnClick = { onBtnClick(name, date) }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            HighlightedText(text = "일정제목")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                text = name,
                onTextChange = { name = it },
                placeholder = "최소 2자 이상"
            )
            Spacer(modifier = Modifier.height(20.dp))
            HighlightedText(text = "날짜 선택")
            Spacer(modifier = Modifier.height(8.dp))
            TextFieldBtn(text = date, placeholder = "날짜를 선택해주세요") {
                timerModalState = true
            }
            if (timerModalState) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    DatePickerModal(
                        defaultDate = date,
                        onCancel = { timerModalState = false },
                        onConfirm = {
                            date = it
                            timerModalState = false
                        }
                    )
                }
            }
        }
    }
}