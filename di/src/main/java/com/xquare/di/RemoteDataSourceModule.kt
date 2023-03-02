package com.xquare.di

import com.xquare.data.remote.datasource.AuthRemoteDataSource
import com.xquare.data.remote.datasource.AuthRemoteDataSourceImpl
import com.xquare.data.remote.datasource.MealRemoteDataSource
import com.xquare.data.remote.datasource.MealRemoteDataSourceImpl
import com.xquare.data.remote.datasource.UserSimpleRemoteDataSource
import com.xquare.data.remote.datasource.UserSimpleRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindRemoteMealDataSource(
        mealRemoteDataSourceImpl: MealRemoteDataSourceImpl
    ): MealRemoteDataSource

    @Binds
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    abstract fun bindSimpleUserRemoteDataSource(
        userSimpleRemoteDataSourceImpl: UserSimpleRemoteDataSourceImpl
    ): UserSimpleRemoteDataSource
}