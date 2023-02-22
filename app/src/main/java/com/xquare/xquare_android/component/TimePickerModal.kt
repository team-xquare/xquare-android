package com.xquare.xquare_android.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chargemap.compose.numberpicker.ListItemPicker
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.purple.purple400
import com.semicolon.design.color.primary.white.white
import com.semicolon.design.notoSansFamily



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimePickerDialog(
    defaultHour: String = "00",
    defaultMin: String = "00",
    cancelText: String = "취소하기",
    confirmText: String = "선택하기",
    onCancel: () -> Unit,
    onConfirm: (String, String) -> Unit,
) {
    var hour by remember { mutableStateOf(defaultHour) }
    var min by remember { mutableStateOf(defaultMin) }

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
                    value = hour,
                    itemList = (0..24).settingTimerList(),
                ) {
                    hour = it
                }
                Spacer(modifier = Modifier.size(24.dp))
                PickerItem(
                    value = min,
                    itemList = (0..60).settingTimerList(),
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
                    onConfirm(hour, min)
                }
            }
        }
    }
}

private fun IntRange.settingTimerList():
        List<String> = (this).map { it.subString() }

private fun Int.subString() = "%02d".format(this)

@Composable
fun PickerItem(
    value: String,
    itemList: List<String>,
    onValueChange: (String) -> Unit,
) {
    ListItemPicker(
        label = { it },
        list = itemList,
        value = value,
        onValueChange = onValueChange,
        dividersColor = purple400,
        textStyle = TextStyle(
            color = gray900,
            lineHeight = 21.sp,
            letterSpacing = 0.sp,
            fontSize = 14.sp,
            fontFamily = notoSansFamily,
            fontWeight = FontWeight.Normal,
            textDecoration = null,
            textAlign = null,
        )
    )
}

@Composable
@Preview(showBackground = true)
fun ShowTimePickerModal() {
    TimePickerDialog(
        onCancel = {  },
        onConfirm = { hour, min ->
            Log.d("TAG", "ShowTimePickerModal: $hour:$min")
        }
    )
}
