package com.xquare.xquare_android.component.modal

import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.NumberPicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xquare.xquare_android.R
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject

internal fun IntRange.settingHourList():
        Array<String> = (this.map { it.subString() }).toTypedArray()

internal fun IntRange.settingMinList():
        Array<String> = (this.map { (it*10).subString() }).toTypedArray()

internal fun IntRange.settingCalendarList():
        Array<String> = (this.map { it.subString() }).toTypedArray()

internal fun String.subString() = "%02d".format(this.toInt())

internal fun Int.subString() = "%02d".format(this)

internal fun IntRange.settingPeriodList():
        Array<String> = (this.map { it.toString() }).toTypedArray()

internal fun lastDay(year: Int, month: Int): Int {
    val cal = Calendar.getInstance()
    cal.set(year,month - 1,1)
    return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
}

internal fun dayOver(
    year: Int,
    month: Int,
    day: Int
): Int {
    val cal = Calendar.getInstance()
    cal.set(year, month - 1, 1)
    val lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

    return if (day > lastDay) lastDay
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

@Composable
fun CalendarPickerItem(
    defaultValue: Int,
    itemList: Array<String>,
    onValueChange: (Int) -> Unit,
) {

    AndroidView(
        factory = { context ->
            val style = R.style.PickerItemStyle
            NumberPicker(ContextThemeWrapper(context, style)).apply {
                minValue = 1
                maxValue = itemList.size
                value = defaultValue
                displayedValues = itemList
                this.setOnValueChangedListener { _,_,value ->
                    onValueChange(value)
                }
            }
        },
        update = {
            try {
                it.displayedValues = itemList
                it.maxValue = itemList.size
            } catch (e: ArrayIndexOutOfBoundsException) {
                it.maxValue = itemList.size
                it.displayedValues = itemList
            }
            it.value = defaultValue
        }
    )
}
