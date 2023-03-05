package com.xquare.xquare_android.component.modal

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.math.ceil

@RequiresApi(Build.VERSION_CODES.O)
fun String.setDefaultTime(): String = this.ifEmpty {
    LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun String.addZeroBack(length: Int): String =
    this.padEnd(length,'0')

@RequiresApi(Build.VERSION_CODES.O)
fun String.toHourMin(): LocalTime {
    return try {
        val localTime = LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm"))
        localTime
    } catch (e: DateTimeParseException) {
        LocalTime.of(0,0)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalTime.toMinCail(): LocalTime {
    var hour = this.hour
    var minute = ceil(this.minute.toDouble()/10).toInt()*10
    if (minute >= 60) {
        minute -= 60
        if (hour == 23) { hour = 0 } else { hour ++ }
    }
    return LocalTime.of(hour, minute)
}



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimePickerDialog(
    defaultTime: String,
    cancelText: String = "취소하기",
    confirmText: String = "선택하기",
    onCancel: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    val defaultTime = defaultTime.setDefaultTime()
    var hour by remember { mutableStateOf(defaultTime.toHourMin().toMinCail().hour.toString().addZeroBack(2)) }
    var min by remember { mutableStateOf(defaultTime.toHourMin().toMinCail().minute.toString().addZeroBack(2)) }

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
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                PickerItem(
                    defaultValue = hour,
                    itemList = (0..23).settingHourList(),
                ) {
                    hour = it
                }
                Spacer(modifier = Modifier.size(24.dp))
                MinPickerItem(
                    defaultValue = min[0].toString(),
                    itemList = (0..5).settingMinList(),
                ) {
                    min = it
                }
            }
            Spacer(Modifier.size(16.dp))
            Row {
                DefaultModalButton(
                    Modifier.weight(1f), text = cancelText) { onCancel() }
                Spacer(Modifier.size(16.dp))
                PrimaryModalButton(
                    Modifier.weight(1f), text = confirmText) {
                    onConfirm("$hour:$min")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun ShowTimePickerModal() {
    TimePickerDialog(
        defaultTime = "34:32",
        onCancel = {
        },
    ) {
        Log.d("TAG", "Time: $it")
    }

}
