package com.xquare.xquare_android.component.modal

import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.NumberPicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.viewinterop.AndroidView
import com.xquare.xquare_android.R
import java.util.*

//all
internal fun IntRange.settingHourList():
        Array<String> = (this.map { it.subString() }).toTypedArray()

internal fun IntRange.settingMinList():
        Array<String> = (this.map { (it*10).subString() }).toTypedArray()

internal fun Int.subString() = "%02d".format(this)

internal fun IntRange.settingPeriodList():
        Array<String> = (this.map { it.toString() }).toTypedArray()

internal fun lastDay(year: String, month: String): Int {
    val cal = Calendar.getInstance()
    cal.set(year.toInt(),month.toInt() - 1,1)
    return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
}

internal fun dayOver(
    year: String,
    month: String,
    day: String
): String {
    val cal = Calendar.getInstance()
    cal.set(year.toInt(), month.toInt() - 1, 1)
    val lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

    return if (day.toInt() > lastDay) lastDay.toString()
    else day
}

@Composable
fun PickerItem(
    defaultValue: String,
    itemList: Array<String>,
    onValueChange: (String) -> Unit,
) {
    AndroidView(
        factory = { context ->
            val style = R.style.PickerItemStyle
            NumberPicker(ContextThemeWrapper(context, style)).apply {
                minValue = 0
                maxValue = itemList.size - 1
                value = defaultValue.toInt()
                displayedValues = itemList
                this.setOnValueChangedListener { _,_,value ->
                    onValueChange(value.subString())
                }
            }
        }
    )
}

@Composable
fun MinPickerItem(
    defaultValue: String,
    itemList: Array<String>,
    onValueChange: (String) -> Unit,
) {
    AndroidView(
        factory = { context ->
            val style = R.style.PickerItemStyle
            NumberPicker(ContextThemeWrapper(context, style)).apply {
                minValue = 0
                maxValue = itemList.size - 1
                value = defaultValue.toInt()
                displayedValues = itemList
                this.setOnValueChangedListener { _,_,value ->
                    onValueChange(value.toString() + "0")
                }
            }
        }
    )
}

@Composable
fun PeriodPickerItem(
    defaultValue: String,
    itemList: Array<String>,
    onValueChange: (String) -> Unit,
) {
    AndroidView(
        factory = { context ->
            val style = R.style.PickerItemStyle
            NumberPicker(ContextThemeWrapper(context, style)).apply {
                minValue = 1
                maxValue = itemList.size
                value = defaultValue.toInt()
                displayedValues =
                    itemList.map {
                        buildAnnotatedString {
                            append("            ")
                            append(it)
                            append("교시")
                            append("            ")
                        }.toString()
                    }.toTypedArray()
                this.setOnValueChangedListener { _,_,value ->
                    onValueChange(value.toString())
                }
            }
        }
    )
}