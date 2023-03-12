package com.xquare.xquare_android.widget

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import com.xquare.xquare_android.R
import com.xquare.xquare_android.feature.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("RemoteViewLayout")
class MealWidget @Inject constructor() : AppWidgetProvider() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        val localDateTime = LocalDateTime.now()
        val date = localDateTime.toLocalDate()
        val time = localDateTime.toLocalTime()

        val builder = WidgetRetrofitBuilder()
        appWidgetIds?.forEach { appWidgetId ->
            builder.widgetApi().getMeal().enqueue()
            val view = RemoteViews(context?.packageName, R.layout.meal_widget)
            appWidgetManager?.updateAppWidget(appWidgetId,view)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val view = RemoteViews(context?.packageName, R.layout.meal_widget)
    }
}