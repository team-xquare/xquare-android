package com.xquare.di

import com.xquare.data.remote.datasource.*
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
    abstract fun bindPointRemoteDataSource(
        pointRemoteDataSourceImpl: PointRemoteDataSourceImpl
    ): PointRemoteDataSource

    @Binds
    abstract fun bindUserRemoteDataSource(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource

    @Binds
    abstract fun bindSimpleUserRemoteDataSource(
        userSimpleRemoteDataSourceImpl: UserSimpleRemoteDataSourceImpl
    ): UserSimpleRemoteDataSource

    @Binds
    abstract fun bindTimetablesDataSource(
        timetablesRemoteDataSourceImpl: TimetablesRemoteDataSourceImpl
    ): TimetablesRemoteDataSource

    @Binds
    abstract fun bindSchedulesRemoteDataSource(
        schedulesRemoteDataSourceImpl: SchedulesRemoteDataSourceImpl
    ): SchedulesRemoteDataSource

    @Binds
    abstract fun bindAttachmentRemoteDataSource(
        attachmentRemoteDataSourceImpl: AttachmentRemoteDataSourceImpl
    ): AttachmentRemoteDataSource
}