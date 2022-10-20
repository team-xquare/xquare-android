package com.xquare.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.google.gson.Gson
import com.xquare.data.dao.MealDao
import com.xquare.data.local.XquareDatabase
import com.xquare.data.local.entity.meals.AllMealEntityTypeConverter
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
        @ApplicationContext context: Context
    ): XquareDatabase =
        Room.databaseBuilder(context, XquareDatabase::class.java, "XquareDatabase")
            .build()

    @Provides
    fun provideMealDao(
        xquareDatabase: XquareDatabase
    ): MealDao = xquareDatabase.mealDao()
}