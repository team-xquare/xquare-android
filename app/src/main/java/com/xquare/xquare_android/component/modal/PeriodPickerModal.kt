package com.xquare.xquare_android.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PeriodPickerModal(
    defaultPeriod: String,
    cancelText: String = "취소하기",
    confirmText: String = "선택하기",
    onCancel: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    val defaultPeriod = defaultPeriod.ifEmpty { "1" }
    var period by remember { mutableStateOf(defaultPeriod) }

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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.size(12.dp))

            PeriodPickerItem(
                defaultValue = period,
                itemList = (1..10).settingPeriodList(),
            ) {
                period = it
            }

            Spacer(Modifier.size(16.dp))
            Row {
                DefaultModalButton(
                    Modifier.weight(1f), text = cancelText) { onCancel() }
                Spacer(Modifier.size(16.dp))
                PrimaryModalButton(
                    Modifier.weight(1f), text = confirmText) { onConfirm(defaultPeriod) }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewPeriodPicker() {
    PeriodPickerModal(
        defaultPeriod = "2",
        onCancel = {

        },
        onConfirm = {

        }
    )
}