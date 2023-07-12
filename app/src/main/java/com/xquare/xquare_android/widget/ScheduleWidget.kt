package com.xquare.xquare_android.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.xquare.xquare_android.MainActivity
import com.xquare.xquare_android.R
import org.threeten.bp.LocalDate

class ScheduleWidget : GlanceAppWidget() {

    @Composable
    override fun Content() {
        ScheduleWidgetScreen()
    }
}

@Composable
fun ScheduleWidgetScreen() {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .padding(4.dp)
            .clickable(actionStartActivity<MainActivity>())
            .background(Color.White),
    ) {
        val now = LocalDate.now()
        val schedule by remember { mutableStateOf(WidgetReceiver.schedule.schedules) }

        Row {
            Text(
                text = now.toString().substring(8, 10) + "일",
                style = TextStyle(
                    color = ColorProvider(R.color.black),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                )
            )
            Spacer(modifier = GlanceModifier.size(8.dp))
            Text(
                text = now.dayOfWeek.toString().substring(0, 3),
                style = TextStyle(
                    color = ColorProvider(R.color.gray400),
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                ),
            )
        }
        for (i: Int in schedule.indices step (1)) {
            if (schedule[i].date == now.toString()) {
                Row(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .clickable(actionStartActivity<MainActivity>()),
                    horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
                    verticalAlignment = Alignment.Vertical.CenterVertically
                ) {
                    Text(
                        text = schedule[i].name,
                        style = TextStyle(
                            color = ColorProvider(R.color.black),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
                break
            }
            if (i == schedule.size - 1) {
                Row(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .clickable(actionStartActivity<MainActivity>()),
                    horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
                    verticalAlignment = Alignment.Vertical.CenterVertically
                ) {
                    Text(
                        text = "오늘은 일정이 없어요 ☃️",
                        style = TextStyle(
                            color = ColorProvider(R.color.black),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
            }
        }
    }
}