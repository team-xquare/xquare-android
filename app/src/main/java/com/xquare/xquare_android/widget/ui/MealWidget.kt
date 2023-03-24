package com.xquare.xquare_android.widget.ui

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.xquare.domain.entity.meal.MealEntity
import com.xquare.xquare_android.MainActivity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.widget.data.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
@SuppressLint("WeekBasedYear")
@RequiresApi(Build.VERSION_CODES.O)
class MealWidget(): BaseWidget(), CoroutineScope by MainScope() {


    private val meals = "정보를 불러오지 못했다 이새기야"

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)

    }

    @SuppressLint("RemoteViewLayout")
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )


        val meal = getMeal(context)

        val views = RemoteViews(context?.packageName, R.layout.meal_widget).apply {
            setOnClickPendingIntent(R.id.meal_widget, pendingIntent)
            setTextViewText(R.id.meal, meal.meal)
            setTextViewText(R.id.time, setTime())
            setTextViewText(R.id.date, setDate())
            setTextViewText(R.id.calories, meal.calories)
        }

        views.setOnClickPendingIntent(R.id.meal_widget, pendingIntent)

        appWidgetManager!!.apply {
            updateAppWidget(appWidgetIds, views)
            notifyAppWidgetViewDataChanged(appWidgetIds, R.id.meal_widget)
        }
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
    private fun setMeal(context: Context?): MealWidgetState {
        val localDateTime = LocalDateTime.now()
        val date = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val mealWidget = getMeal(context)

        val builder = WidgetRetrofitBuilder
        builder.widgetApi().getMeal(date).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {

                Log.e("RESPONSE", "onResponse: ${response.body()}", )
                val meal = MealEntity(breakfast = listOf(""), lunch = listOf(""), dinner = listOf(""), "", "", "")
                val mealList = response.body()?.breakfast
                if (!mealList.isNullOrEmpty()) {
                    meal.breakfast
                    meal.caloriesOfBreakfast
                }
            }
            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
        val time = LocalDateTime.now()

        val mealEntity = MealEntity(
            breakfast = listOf(),
            lunch = listOf(),
            dinner = listOf(),
            "","",""
        )

        val currentMealType = MealType.getCurrentMealType(time)


        val meal = when (currentMealType) {
            MealType.Breakfast -> mealEntity.breakfast
            MealType.Lunch -> mealEntity.lunch
            MealType.Dinner -> mealEntity.dinner
        }

        val mealNotFound: Boolean = meal.size > 1

        return MealWidgetState(
            mealType = currentMealType,
            meal = if (mealNotFound) meal
                .dropLast(1)
                .joinToString ("\n") else context!!.getString(R.string.app_name),
            date = LocalDate.now(),
            calories = if (mealNotFound) meal.last() else "이건 뭐..뭐노??"
        )
    }
}
