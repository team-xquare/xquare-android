package com.xquare.xquare_android.component

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.semicolon.design.Body2
import com.semicolon.design.color.system.red.red400

@Composable
fun HighlightedText(text: String) {
    Row {
        Body2(text = text)
        Body2(text = "*", color = red400)
    }
}