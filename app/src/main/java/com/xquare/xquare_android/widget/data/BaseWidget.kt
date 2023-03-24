package com.xquare.xquare_android.widget.data

import android.appwidget.AppWidgetProvider
import android.content.Context
import android.nfc.Tag
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.xquare.domain.entity.meal.MealEntity
import com.xquare.domain.usecase.meal.FetchTodayMealUseCase
import com.xquare.xquare_android.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
abstract class BaseWidget : AppWidgetProvider(), CoroutineScope by MainScope() {



    @Inject
    lateinit var mealWidgetUseCase : FetchTodayMealUseCase

    @RequiresApi(Build.VERSION_CODES.O)
     fun getMeal(
        context: Context?
    ): MealWidgetState {
        val time = LocalDateTime.now()

        val mealWidgetEntity = MealEntity(
            breakfast = listOf(context!!.getString(R.string.MealError)),
            lunch = listOf(context.getString(R.string.MealError)),
            dinner = listOf(context.getString(R.string.MealError)),
            "",
            "",
            "" ,
        )


        val mealType = MealType.getCurrentMealType(time)

        val meal = when (mealType) {
            MealType.Breakfast -> mealWidgetEntity.breakfast
            MealType.Lunch -> mealWidgetEntity.lunch
            MealType.Dinner -> mealWidgetEntity.dinner
        }

        val mealNotFound: Boolean = meal.size > 1

        return MealWidgetState(
            mealType = mealType,
            meal = if (mealNotFound) meal
                .dropLast(1)
                .joinToString("\n") else context.getString(R.string.app_name),
            date = LocalDate.now(),
            calories = if (mealNotFound) meal.last() else "이건..뭐..뭐농"
        )
    }
}