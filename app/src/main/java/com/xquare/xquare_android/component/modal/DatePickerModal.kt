package com.xquare.xquare_android.component.modal

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.component.DefaultModalButton
import com.xquare.xquare_android.component.PrimaryModalButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
private fun String.setDefaultDay(): LocalDate =
    if (this.isEmpty()) {
        LocalDate.now()
    } else {
        try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            LocalDate.parse(this, formatter)
        } catch (e: DateTimeParseException) {
            LocalDate.now()
        }
    }

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DatePickerModal(
    defaultDate: String,
    cancelText: String = "취소하기",
    confirmText: String = "선택하기",
    onCancel: () -> Unit,
    onConfirm: (String) -> Unit,
) {

    val defaultDate = defaultDate.setDefaultDay()
    var year by remember { mutableStateOf(defaultDate.year) }
    var month by remember { mutableStateOf(defaultDate.monthValue) }
    var day by remember { mutableStateOf(defaultDate.dayOfMonth) }

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = onCancel
    ) {
        Column(
            Modifier
                .padding(horizontal = 32.dp)
                .background(color = white, shape = RoundedCornerShape(12.dp))
                .padding(20.dp),
        ) {
            Spacer(Modifier.size(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                CalendarPickerItem(
                    defaultValue = year,
                    itemList = (year..year).settingCalendarList(),
                ) {
                    year = it
                }
                Spacer(modifier = Modifier.size(24.dp))
                CalendarPickerItem(
                    defaultValue = month,
                    itemList = (1..12).settingCalendarList(),
                ) {
                    month = it
                    day = dayOver(year, month, day)
                }
                Spacer(modifier = Modifier.size(24.dp))
                CalendarPickerItem(
                    defaultValue = day,
                    itemList = (1..lastDay(year, month)).settingCalendarList(),
                ) {
                    day = it
                }
            }
            Spacer(Modifier.size(16.dp))
            Row {
                DefaultModalButton(
                    Modifier.weight(1f), text = cancelText) { onCancel() }
                Spacer(Modifier.size(16.dp))
                PrimaryModalButton(
                    Modifier.weight(1f), text = confirmText) {
                    onConfirm("$year-${month.subString()}-${day.subString()}")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun ShowDatePickerModal() {
    DatePickerModal(
        defaultDate = "2023-10-09",
        onCancel = {
        },
        onConfirm = {
            Log.d("TAG", "ShowDatePickerModal: $it")
        }
    )
}