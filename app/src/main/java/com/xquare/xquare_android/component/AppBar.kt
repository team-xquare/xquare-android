package com.xquare.xquare_android.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.black.black
import com.semicolon.design.color.primary.gray.gray50
import com.xquare.xquare_android.R

@Composable
fun AppBar(
    painter: Painter? = null,
    text: String,
    backgroundColor: Color = Color.Unspecified,
    onIconClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.size(16.dp))
        painter?.let {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        enabled = true
                    ) { onIconClick() },
                painter = it,
                contentDescription = null
            )
            Spacer(Modifier.size(12.dp))
        }
        Subtitle4(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    AppBar(painter = painterResource(R.drawable.ic_placeholder), text = "Preview") {}
}
