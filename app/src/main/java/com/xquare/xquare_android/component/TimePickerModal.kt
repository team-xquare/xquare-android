package com.xquare.xquare_android.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.semicolon.design.Body2
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.purple.purple400
import com.semicolon.design.notoSansFamily


@Composable
@Preview(showBackground = true)
fun ShowTimePickerModal() {
    val possibleValues = listOf("00","01","02","03")
    var state by remember { mutableStateOf("01") }

    ListItemPicker(
        label = { it },
        list = possibleValues,
        value = state,
        onValueChange = { state = it },
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
