package com.xquare.xquare_android.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.semicolon.design.Button
import com.semicolon.design.button.BasicButton
import com.semicolon.design.color.primary.gray.*
import com.semicolon.design.color.primary.purple.*
import com.semicolon.design.color.primary.white.white

@Composable
fun PrimaryModalButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicButton(
        defaultColor = purple400,
        pressedColor = purple500,
        disabledColor = purple50,
        isEnabled = isEnabled,
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(8.dp)),
        onClick = onClick
    ) {
        Button(text = text, color = white)
    }
}

@Composable
fun DefaultModalButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    val textColor = if (isEnabled) gray700 else gray300
    BasicButton(
        defaultColor = gray50,
        pressedColor = gray100,
        disabledColor = gray50,
        isEnabled = isEnabled,
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(8.dp)),
        onClick = onClick
    ) {
        Button(text = text, color = textColor)
    }
}
