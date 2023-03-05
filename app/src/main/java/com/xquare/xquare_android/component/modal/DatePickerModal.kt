package com.xquare.xquare_android.component.modal

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.component.DefaultModalButton
import com.xquare.xquare_android.component.PrimaryModalButton
import java.util.*

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