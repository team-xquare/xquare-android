package com.xquare.xquare_android.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.component.DefaultModalButton
import com.xquare.xquare_android.component.PrimaryModalButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ConfirmModal(
    message: String,
    confirmText: String,
    cancelText: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {

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
            Subtitle4(text = message, color = gray900)
            Spacer(Modifier.size(16.dp))
            Row {
                DefaultModalButton(
                    Modifier.weight(1f), text = cancelText) { onCancel() }
                Spacer(Modifier.size(16.dp))
                PrimaryModalButton(
                    Modifier.weight(1f), text = confirmText) { onConfirm() }
            }
        }

    }
}