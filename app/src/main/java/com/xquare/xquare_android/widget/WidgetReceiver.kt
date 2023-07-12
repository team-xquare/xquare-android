package com.xquare.xquare_android.widget

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.xquare.domain.entity.schedules.SchedulesEntity
import com.xquare.domain.usecase.schedules.FetchSchedulesUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.scheduleAtFixedRate

@AndroidEntryPoint
class WidgetReceiver: GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = ScheduleWidget()

    @Inject
    lateinit var fetchSchedulesUseCase: FetchSchedulesUseCase

    companion object {
        var schedule: SchedulesEntity = SchedulesEntity(schedules = listOf())
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Timer().scheduleAtFixedRate(21600000,21600000) {
            updateAppWidget(context = context)
        }
    }

    private fun updateAppWidget(context: Context) {
        updateWidgetData()
        val updateIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        val widgetComponent = ComponentName(context, WidgetReceiver::class.java)
        val widgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(widgetComponent)
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds)
        context.sendBroadcast(updateIntent)
    }

    @SuppressLint("NewApi")
    private fun updateWidgetData() {
        CoroutineScope(Dispatchers.IO).launch {
            val month = LocalDateTime.now().monthValue
            schedule = fetchSchedulesUseCase.execute(month)
        }
    }
}