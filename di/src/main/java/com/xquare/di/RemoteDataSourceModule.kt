package com.xquare.di

import com.xquare.data.remote.datasource.*
import com.xquare.data.remote.datasource.alarm.AlarmRemoteDataSource
import com.xquare.data.remote.datasource.alarm.AlarmRemoteDataSourceImpl
import com.xquare.data.remote.datasource.attachment.AttachmentRemoteDataSource
import com.xquare.data.remote.datasource.attachment.AttachmentRemoteDataSourceImpl
import com.xquare.data.remote.datasource.auth.AuthRemoteDataSource
import com.xquare.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.xquare.data.remote.datasource.github.GithubRemoteDataSource
import com.xquare.data.remote.datasource.github.GithubRemoteDataSourceImpl
import com.xquare.data.remote.datasource.meal.MealRemoteDataSource
import com.xquare.data.remote.datasource.meal.MealRemoteDataSourceImpl
import com.xquare.data.remote.datasource.pick.PickRemoteDataSource
import com.xquare.data.remote.datasource.pick.PickRemoteDataSourceImpl
import com.xquare.data.remote.datasource.point.PointRemoteDataSource
import com.xquare.data.remote.datasource.point.PointRemoteDataSourceImpl
import com.xquare.data.remote.datasource.report.ReportRemoteDataSource
import com.xquare.data.remote.datasource.report.ReportRemoteDataSourceImpl
import com.xquare.data.remote.datasource.schedules.SchedulesRemoteDataSource
import com.xquare.data.remote.datasource.schedules.SchedulesRemoteDataSourceImpl
import com.xquare.data.remote.datasource.timetable.TimetablesRemoteDataSource
import com.xquare.data.remote.datasource.timetable.TimetablesRemoteDataSourceImpl
import com.xquare.data.remote.datasource.user.UserRemoteDataSource
import com.xquare.data.remote.datasource.user.UserRemoteDataSourceImpl
import com.xquare.data.remote.datasource.user.UserSimpleRemoteDataSource
import com.xquare.data.remote.datasource.user.UserSimpleRemoteDataSourceImpl
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

    @Binds
    abstract fun bindAlarmRemoteDataSource(
        alarmRemoteDataSourceImpl: AlarmRemoteDataSourceImpl
    ): AlarmRemoteDataSource

    @Binds
    abstract fun bindPickRemoteDataSource(
        pickRemoteDataSourceImpl: PickRemoteDataSourceImpl
    ): PickRemoteDataSource

    @Binds
    abstract fun bindReportsRemoteDataSource(
        reportRemoteDataSourceImpl: ReportRemoteDataSourceImpl
    ): ReportRemoteDataSource

    @Binds
    abstract fun bindGithubRemoteDataSource(
        githubRemoteDataSourceImpl: GithubRemoteDataSourceImpl
    ): GithubRemoteDataSource
}