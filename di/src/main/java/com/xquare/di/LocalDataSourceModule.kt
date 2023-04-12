package com.xquare.di

import com.xquare.data.local.datasource.*
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

    @Binds
    abstract fun bindTimetableLocalDataSource(
        timetableLocalDataSourceImpl: TimetableLocalDataSourceImpl
    ): TimetableLocalDataSource

    @Binds
    abstract fun bindPointLocalDataSource(
        pointLocalDataSourceImpl: PointLocalDataSourceImpl
    ): PointLocalDataSource

    @Binds
    abstract fun bindHomeUserLocalDataSource(
        homeUserLocalDataSourceImpl: HomeUserLocalDataSourceImpl
    ): HomeUserLocalDataSource

}