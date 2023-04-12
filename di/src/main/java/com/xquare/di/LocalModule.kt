package com.xquare.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.xquare.data.dao.*
import com.xquare.data.local.XquareDatabase
import com.xquare.data.local.entity.alarm.AlarmEntityTypeConverter
import com.xquare.data.local.entity.homeUser.HomeUserEntityTypeConverter
import com.xquare.data.local.entity.meals.AllMealEntityTypeConverter
import com.xquare.data.local.entity.meals.MealEntityTypeConverter
import com.xquare.data.local.entity.point.PointEntityTypeConverter
import com.xquare.data.local.entity.timetable.TimetableEntityTypeConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideSharedPreference(
        @ApplicationContext context: Context
    ): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    fun provideXquareDatabase(
        @ApplicationContext context: Context,
        mealEntityTypeConverter: MealEntityTypeConverter,
        allMealEntityTypeConverter: AllMealEntityTypeConverter,
        alarmEntityTypeConverter: AlarmEntityTypeConverter,
        timetableEntityTypeConverter: TimetableEntityTypeConverter,
        pointEntityTypeConverter: PointEntityTypeConverter,
        homeUserEntityTypeConverter: HomeUserEntityTypeConverter
    ): XquareDatabase =
        Room.databaseBuilder(context, XquareDatabase::class.java, "XquareDatabase")
            .addTypeConverter(mealEntityTypeConverter)
            .addTypeConverter(allMealEntityTypeConverter)
            .addTypeConverter(alarmEntityTypeConverter)
            .addTypeConverter(timetableEntityTypeConverter)
            .addTypeConverter(pointEntityTypeConverter)
            .addTypeConverter(homeUserEntityTypeConverter)
            .build()

    @Provides
    fun provideMealDao(
        xquareDatabase: XquareDatabase
    ): MealDao = xquareDatabase.mealDao()

    @Provides
    fun provideAlarmDao(
        xquareDatabase: XquareDatabase
    ): AlarmDao = xquareDatabase.alarmDao()

    @Provides
    fun provideTimetableDao(
        xquareDatabase: XquareDatabase
    ): TimetableDao = xquareDatabase.timetableDao()

    @Provides
    fun providePointDao(
        xquareDatabase: XquareDatabase
    ): PointDao = xquareDatabase.pointDao()

    @Provides
    fun provideHomeUserDao(
        xquareDatabase: XquareDatabase
    ): HomeUserDao = xquareDatabase.homeUserDao()
}