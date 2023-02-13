package com.xquare.xquare_android.component

import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.semicolon.design.color.primary.purple.purple400
import com.semicolon.design.color.primary.white.white
import com.semicolon.design.color.system.blue.blue400
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimePickerModal(
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
        onDismissRequest = onCancel
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .background(color = white, RoundedCornerShape(12.dp))
                .padding(20.dp),
        ) {

            Spacer(Modifier.size(16.dp))
            Row {
                DefaultModalButton(
                    Modifier.weight(1f), text = "취소하기") { onCancel() }
                Spacer(Modifier.size(16.dp))
                PrimaryModalButton(
                    Modifier.weight(1f), text = "선택하기") { onConfirm() }
            }
        }
    }
}

@Composable
fun <T>TimePicker(
    list: List<T>,
    onValueChanged: (T) -> Unit,
    showAmount: Int = 10
) {
    val listCount by remember { mutableStateOf(list.size) }

    val correctionValue by remember {
        if (list.size%2 == 0) {
            mutableStateOf(1)
        } else {
            mutableStateOf(0)
        }
    }

    var dragStartedY by remember {
        mutableStateOf(0f)
    }

    var currentDragY by remember {
        mutableStateOf(0f)
    }

    var oldY by remember {
        mutableStateOf(0f)
    }

    Canvas(
        modifier = Modifier
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = {
                        dragStartedY = it.y
                    },
                    onDragEnd = {
                        val spacerPerItem = size.height / showAmount
                        val rest = currentDragY % spacerPerItem

                        val roundUp = abs(rest / spacerPerItem).roundToInt() == 1
                        val newY = if (roundUp) {
                            if (rest < 0) {
                                currentDragY + abs(rest) - spacerPerItem
                            } else {
                                currentDragY - rest + spacerPerItem
                            }
                        } else {
                            if (rest < 0) {
                                currentDragY + abs(rest)
                            } else {
                                currentDragY - rest
                            }
                        }
                        currentDragY = newY.coerceIn(
                            minimumValue = -(listCount/2f) * spacerPerItem,
                            maximumValue = listCount/2f*spacerPerItem
                        )
                        val index = ((listCount/2)+(currentDragY/spacerPerItem)).toInt()
                        onValueChanged(list[index])
                        oldY = currentDragY
                    },
                    onDrag = { change, dragAmount ->
                        val changeY = change.position.y
                        val newY = oldY + (dragStartedY - changeY)
                        val spacerPerItem = size.height / showAmount
                        currentDragY = newY.coerceIn(
                            minimumValue = -(listCount / 2f) * spacerPerItem,
                            maximumValue = (listCount / 2f) * spacerPerItem
                        )
                        val index = (listCount / 2) + (currentDragY / spacerPerItem).toInt()
                        onValueChanged(list[index])
                    }
                )
            }
            .fillMaxWidth()
            .height(60.dp)
    ) {
        val top = 0f
        val bot = size.height

        drawContext.canvas.nativeCanvas.apply {
            drawRect(
                Rect(-2000, top.toInt(), size.width.toInt() + 2000, bot.toInt()),
                Paint().apply {
                    color = white.copy(0.8f).toArgb()
                    setShadowLayer(
                        30f, 0f, 0f, android.graphics.Color.argb(50,0,0,0)
                    )
                }
            )
        }
        val spaceForEachItem = size.height/showAmount
        for (i in 0 until listCount) {
            val currentY = i * spaceForEachItem - currentDragY -
                    ((listCount-1+correctionValue - showAmount)/2*spaceForEachItem)

            drawContext.canvas.nativeCanvas.apply {
                val x = 45f + 5.dp.toPx() + 16.sp.toPx()

                drawText(
                    list[i].toString(),
                    x,
                    currentY,
                    Paint().apply {
                        textSize = 16.sp.toPx()
                        textAlign = Paint.Align.CENTER
                        isFakeBoldText = true
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ShowTimePickerModal() {

    var num by remember { mutableStateOf("1") }

    Column(
        modifier = Modifier
            .background(white)
            .fillMaxHeight()
    ) {
        TimePicker(
            list = (1..31).toList(),
            onValueChanged = {
                num = it.toString()
            },
            showAmount = 2
        )
    }

    Log.d("TAG", "num: $num")
}
