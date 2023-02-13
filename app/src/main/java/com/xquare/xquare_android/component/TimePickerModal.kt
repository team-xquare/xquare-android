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

data class PickerStyle(
    val lineColor: Color = purple400,
)

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

    var dragStartedX by remember {
        mutableStateOf(0f)
    }

    var currentDragX by remember {
        mutableStateOf(0f)
    }

    var oldX by remember {
        mutableStateOf(0f)
    }

    Canvas(
        modifier = Modifier
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = {
                        dragStartedX = it.x
                    },
                    onDragEnd = {
                        val spacerPerItem = size.width / showAmount
                        val rest = currentDragX % spacerPerItem

                        val roundUp = abs(rest / spacerPerItem).roundToInt() == 1
                        val newX = if (roundUp) {
                            if (rest < 0) {
                                currentDragX + abs(rest) - spacerPerItem
                            } else {
                                currentDragX - rest + spacerPerItem
                            }
                        } else {
                            if (rest < 0) {
                                currentDragX + abs(rest)
                            } else {
                                currentDragX - rest
                            }
                        }
                        currentDragX = newX.coerceIn(
                            minimumValue = -(listCount/2f) * spacerPerItem,
                            maximumValue = listCount/2f*spacerPerItem
                        )
                        val index = ((listCount/2f)+(currentDragX/spacerPerItem)).toInt()
                        onValueChanged(list[index])
                    },
                    onDrag = { change, dragAmount ->
                        val changeX = change.position.x
                        val newX = oldX + (dragStartedX - changeX)
                        val spacerPerItem = size.width / showAmount
                        currentDragX = newX.coerceIn(
                            minimumValue = -(listCount / 2f) * spacerPerItem,
                            maximumValue = (listCount / 2f) * spacerPerItem
                        )
                        val index = (listCount / 2) + (currentDragX / spacerPerItem).toInt()
                        onValueChanged(list[index])
                        oldX = currentDragX
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
        val spaceForEachItem = size.width/showAmount
        for (i in 0 until listCount) {
            val currentX = i * spaceForEachItem - currentDragX -
                    ((listCount-1+correctionValue - showAmount)/2*spaceForEachItem)

            drawContext.canvas.nativeCanvas.apply {
                val y = 45f + 5.dp.toPx() + 16.sp.toPx()

                drawText(
                    list[i].toString(),
                    currentX,
                    y,
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
            .fillMaxWidth()
    ) {
        TimePicker(
            list = (1..31).toList(),
            onValueChanged = {
                num = it.toString()
            },
            showAmount = 5
        )
    }

    Log.d("TAG", "num: $num")
}
