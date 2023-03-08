package com.xquare.di

import com.xquare.data.local.datasource.AlarmLocalDataSource
import com.xquare.data.local.datasource.AlarmLocalDataSourceImpl
import com.xquare.data.local.datasource.AuthLocalDataSource
import com.xquare.data.local.datasource.AuthLocalDataSourceImpl
import com.xquare.data.local.datasource.MealLocalDataSource
import com.xquare.data.local.datasource.MealLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindAuthLocalDataSource(
        authLocalDataSourceImpl: AuthLocalDataSourceImpl
    ): AuthLocalDataSource

    @Binds
    abstract fun bindMealLocalDataSource(
        mealLocalDataSourceImpl: MealLocalDataSourceImpl
    ): MealLocalDataSource

    @Binds
    abstract fun bindAlarmLocalDataSource(
        alarmLocalDataSourceImpl: AlarmLocalDataSourceImpl
    ): AlarmLocalDataSource
}