package com.xquare.di

import com.xquare.data.repository.AuthRepositoryImpl
import com.xquare.data.repository.meal.MealRepositoryImpl
import com.xquare.data.repository.point.PointRepositoryImpl
import com.xquare.data.repository.user.UserRepositoryImpl
import com.xquare.domain.repository.AuthRepository
import com.xquare.domain.repository.meal.MealRepository
import com.xquare.domain.repository.point.PointRepository
import com.xquare.domain.repository.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMealRepository(
        mealRepositoryImpl: MealRepositoryImpl
    ): MealRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindPointRepository(
        pointRepositoryImpl: PointRepositoryImpl
    ): PointRepository

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}