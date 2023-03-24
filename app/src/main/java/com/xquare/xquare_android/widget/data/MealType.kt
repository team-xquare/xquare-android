package com.xquare.xquare_android.widget.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

enum class MealType(
    val title: String
) {
    Breakfast(title = "아침"),

    Lunch(title = "점심"),

    Dinner(title = "저녁"), ;

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentMealType(localDateTime: LocalDateTime): MealType {
            return when (localDateTime.hour) {
                in MorningStartTime until LunchStartTime -> Breakfast
                in LunchStartTime until DinnerStartTime -> Lunch
                else -> Dinner
            }
        }

        private const val MorningStartTime: Int = 0
        private const val LunchStartTime: Int = 8
        private const val DinnerStartTime: Int = 16
    }
}