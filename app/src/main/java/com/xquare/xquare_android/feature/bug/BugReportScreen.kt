package com.xquare.xquare_android.feature.bug

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.Body3
import com.semicolon.design.color.primary.black.black
import com.semicolon.design.color.primary.gray.gray300
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.purple.purple400
import com.semicolon.design.color.system.red.red400
import com.semicolon.design.notoSansFamily
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.Header
import com.xquare.xquare_android.util.DevicePaddings

// TODO Body4
internal object MenuItem {
    const val HOME = "홈 "
    const val SCHEDULE = "일정"
    const val FEED = "피드"
    const val APPLY = "신청"
    const val ALL = "전체"
}

private fun String.toEntityWhere() =
    when (this) {
        MenuItem.HOME -> "HOME"
        MenuItem.SCHEDULE -> "SCHEDULE"
        MenuItem.FEED -> "FEED"
        MenuItem.APPLY -> "APPLY"
        MenuItem.ALL -> "ALL"
        else -> "HOME"
    }

@Composable
fun BugReportScreen(
    navController: NavController,
) {
    BugreportContent(
        onIconClick = { navController.popBackStack() }
    ) { where, text ->
        Log.d("TAG", "SendBugReportWhere: ${where.toEntityWhere()}")
        Log.d("TAG", "SendBugReportText: $text")
    }
}

@Composable
private fun BugreportContent(
    onIconClick: () -> Unit,
    onBtnClick: (String, String) -> Unit
) {
    var where by remember { mutableStateOf("홈 ") }
    var explanationText by remember { mutableStateOf("") }
    val headerBtnEnabled = explanationText.isNotEmpty()

    Scaffold(
        modifier = Modifier.padding(
            top = DevicePaddings.statusBarHeightDp.dp,
            bottom = DevicePaddings.navigationBarHeightDp.dp
        ),
        topBar = {
            Header(
                painter = painterResource(id = R.drawable.ic_back),
                title = "버그 제보",
                btnText = "제출",
                btnEnabled = headerBtnEnabled,
                onIconClick = onIconClick,
                onBtnClick = {
                    onBtnClick(where, explanationText)
                    explanationText = ""
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            BugreportGuideText(text = "어디서 버그가 발생했나요?")
            Spacer(modifier = Modifier.size(8.dp))
            BugreportWhereMenu(text = where, checkWhere = { where = it })
            Spacer(modifier = Modifier.size(16.dp))
            BugreportGuideText(text = "버그에 대해 요약해서 설명해주세요.")
            Spacer(modifier = Modifier.size(8.dp))
            BugreportExplanationText(
                text = explanationText,
                onTextChange = { explanationText = it },
                placeholder = "설명을 입력해주세요",
            )
        }
    }
}

@Composable
private fun BugreportGuideText(
    text: String
) {
    Row {
        Body2(text = text)
        Body2(text = "*", color = red400)
    }
}

@Composable
private fun BugreportWhereMenu(
    text: String,
    checkWhere: (String) -> Unit
) {
    val menu = listOf("홈 ", "일정", "피드", "신청", "전체")
    var isDrop by remember { mutableStateOf(false) }
    var targetValue by remember { mutableStateOf(-90F) }
    val rotateValue: Float by animateFloatAsState(
        targetValue = targetValue,
        tween(300)
    )

    Column(
        modifier = Modifier
            .width(60.dp)
            .background(
                color = gray50,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                ) {
                    isDrop = true
                    targetValue = 0F
                }
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Body3(text = text)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_drop),
                contentDescription = "menu btn",
                tint = Color(0xFF757575),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End)
                    .size(12.dp)
                    .rotate(rotateValue)
            )
        }
        DropdownMenu(
            expanded = isDrop,
            onDismissRequest = {
                isDrop = false
                targetValue = -90F
            },
            modifier = Modifier
                .width(60.dp)
                .background(
                    color = gray50,
                )
        ) {
            menu.forEach {
                if (it != text) {
                    Spacer(modifier = Modifier.height(3.dp))
                    Body3(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                            ) {
                                checkWhere(it)
                                isDrop = false
                                targetValue = -90F
                            }
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                }
            }
        }
    }
}

@Composable
private fun BugreportExplanationText(
    text: String,
    isSecret: Boolean = false,
    onTextChange: (String) -> Unit,
    placeholder: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        textStyle = TextStyle(
            fontFamily = notoSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = black,
            lineHeight = 24.sp,
        ),
        decorationBox = {
            Box(contentAlignment = Alignment.TopStart) {
                if (text.isEmpty()) {
                    Body1(
                        text = placeholder,
                        color = gray300,
                        modifier = Modifier
                            .height(100.dp)
                            .wrapContentSize(align = Alignment.TopStart)
                    )
                    Box(modifier = Modifier.padding(top = 4.dp)) {
                        it()
                    }
                } else {
                    it()
                }
            }
        },
        keyboardOptions = keyboardOptions,
        cursorBrush = SolidColor(purple400),
        visualTransformation = if (isSecret) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(gray50)
            .border(width = 1.dp, color = gray300, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}