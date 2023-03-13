package com.xquare.xquare_android.widget.ui

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.Global
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.buildAnnotatedString
import com.xquare.domain.repository.meal.MealRepository
import com.xquare.domain.usecase.meal.FetchTodayMealUseCase
import com.xquare.xquare_android.R
import com.xquare.xquare_android.widget.data.MealResponse
import com.xquare.xquare_android.widget.data.WidgetRetrofitBuilder
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@SuppressLint("WeekBasedYear")
@RequiresApi(Build.VERSION_CODES.O)
class MealWidget : AppWidgetProvider() {

    private var meal = "정보를 불러오지 못했습니다."

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)

    }

    @SuppressLint("RemoteViewLayout")
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        setMeal(context)

        val views = RemoteViews(context?.packageName, R.layout.meal_widget)
        views.setOnClickPendingIntent(R.id.meal_widget, setMeal(context))

        views.setTextViewText(R.id.time, setTime())
        views.setTextViewText(R.id.date, setDate())
        views.setTextViewText(R.id.meal, meal)

        appWidgetIds?.forEach { appWidgetId ->
            appWidgetManager?.updateAppWidget(appWidgetId,views)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val views = RemoteViews(context?.packageName, R.layout.meal_widget)
        views.setOnClickPendingIntent(R.id.meal_widget, setMeal(context))

        views.setTextViewText(R.id.time, setTime())
        views.setTextViewText(R.id.date, setDate())
        views.setTextViewText(R.id.meal, meal)
    }

    private fun setTime(): String {
        val time = LocalTime.now()
        val dinner = LocalTime.of(16,0)
        val launch = LocalTime.of(8,0)

        return if (time.isAfter(dinner)) "저녁"
        else if (time.isAfter(launch)) "점심"
        else "아침"
    }

    private fun setDate(): String =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setMeal(context: Context?): PendingIntent {
        val localDateTime = LocalDateTime.now()
        val date = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val builder = WidgetRetrofitBuilder()
        builder.widgetApi().getMeal(date).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                meal = ""
                val mealList = response.body()?.breakfast
                if (!mealList.isNullOrEmpty()) {
                    for (i in mealList.indices) {
                        meal = buildAnnotatedString {
                            append(meal)
                            append(", ")
                            append(mealList[i])
                        }.toString()
                    }
                }
            }
            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                meal = "등록된 정보가\n없습니다."
            }
        })
        val intent = Intent(context, MealWidget::class.java)
        intent.action = "android.appwidget.action.APPWIDGET_UPDATE"
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }
}