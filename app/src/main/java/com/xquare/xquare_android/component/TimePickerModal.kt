package com.xquare.xquare_android.component

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
import java.util.Calendar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimePickerDialog(
    defaultTime: String,
    cancelText: String = "취소하기",
    confirmText: String = "선택하기",
    onCancel: () -> Unit,
    onConfirm: (String) -> Unit,
) {
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DatePickerDialog(
    defaultYear: String? = null,
    defaultMonth: String? = null,
    defaultDate: String? = null,
    cancelText: String = "취소하기",
    confirmText: String = "선택하기",
    onCancel: () -> Unit,
    onConfirm: (String, String, String) -> Unit,
) {
    val calendar = Calendar.getInstance()
    val thisYear = calendar.get(Calendar.YEAR)
    val thisMonth = (calendar.get(Calendar.MONTH) + 1)
    val thisDay = calendar.get(Calendar.DATE)

    var year by remember { mutableStateOf(defaultYear ?: thisYear.toString()) }
    var month by remember { mutableStateOf(defaultMonth ?: thisMonth.subString()) }
    var day by remember { mutableStateOf(defaultDate ?: thisDay.subString()) }

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
                PickerItem(
                    defaultValue = year,
                    itemList = arrayOf(),
                ) {
                    year = it
                    day = dayOver(year,month,day)
                }
                Spacer(modifier = Modifier.size(24.dp))
                PickerItem(
                    defaultValue = month,
                    itemList = arrayOf(),
                ) {
                    month = it
                    day = dayOver(year, month, day)
                }
                Spacer(modifier = Modifier.size(24.dp))
                PickerItem(
                    defaultValue = day,
                    itemList = arrayOf(),
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
                    onConfirm(year, month, day)
                }
            }
        }
    }
}

private fun IntRange.settingTimerList():
        Array<String> = (this.map { it.subString() }).toTypedArray()

private fun Int.subString() = "%02d".format(this)

private fun lastDay(year: String, month: String): Int {
    val cal = Calendar.getInstance()
    cal.set(year.toInt(),month.toInt() - 1,1)
    return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
}

private fun dayOver(
    year: String,
    month: String,
    day: String
): String {
    val cal = Calendar.getInstance()
    cal.set(year.toInt(), month.toInt() - 1, 1)
    val lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

    return if (day.toInt() > lastDay) lastDay.toString()
    else day
}

@Composable
fun PickerItem(
    defaultValue: String,
    itemList: Array<String>,
    onValueChange: (String) -> Unit,
) {
    AndroidView(
        factory = { context ->
            val style = R.style.PickerItemStyle
            NumberPicker(ContextThemeWrapper(context, style)).apply {
                minValue = 0
                maxValue = itemList.size - 1
                value = defaultValue.toInt()
                displayedValues = itemList
                this.setOnValueChangedListener { _,_,value ->
                    onValueChange(value.subString())
                }
            }
        }
    )
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
