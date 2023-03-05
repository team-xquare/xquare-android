package com.xquare.xquare_android.component.modal

import android.os.Build
import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.NumberPicker
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.DefaultModalButton
import com.xquare.xquare_android.component.PrimaryModalButton
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

private fun setDefaultTimer(defaultTime: String): String {
    return defaultTime.ifEmpty {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val thisTime = LocalTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            thisTime.format(formatter)
        } else {
            "00:00"
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimePickerDialog(
    defaultTime: String,
    cancelText: String = "취소하기",
    confirmText: String = "선택하기",
    onCancel: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    val defaultTime = setDefaultTimer(defaultTime)
    var hour by remember { mutableStateOf(defaultTime.substring(0..1)) }
    var min by remember { mutableStateOf(defaultTime.substring(3..4)) }

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
                    itemList = (0..23).settingTimerList(),
                ) {
                    hour = it
                }
                Spacer(modifier = Modifier.size(24.dp))
                PickerItem(
                    defaultValue = min,
                    itemList = (0..59).settingTimerList(),
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

@Composable
@Preview(showBackground = true)
fun ShowTimePickerModal() {
    TimePickerDialog(
        defaultTime = "12:38",
        onCancel = {  },
    ) {
        Log.d("TAG", "Time: $it")
    }

}
