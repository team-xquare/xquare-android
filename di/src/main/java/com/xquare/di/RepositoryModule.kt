package com.xquare.di

import com.xquare.data.repository.AuthRepositoryImpl
import com.xquare.data.repository.GithubRepositoryImpl
import com.xquare.data.repository.PointRepositoryImpl
import com.xquare.data.repository.WebViewRepositoryImpl
import com.xquare.data.repository.attachment.AttachmentRepositoryImpl
import com.xquare.data.repository.meal.MealRepositoryImpl
import com.xquare.data.repository.schedules.SchedulesRepositoryImpl
import com.xquare.data.repository.timetables.TimetablesRepositoryImpl
import com.xquare.data.repository.notification.AlarmRepositoryImpl
import com.xquare.data.repository.pick.PickRepositoryImpl
import com.xquare.data.repository.reports.ReportRepositoryImpl
import com.xquare.data.repository.user.UserRepositoryImpl
import com.xquare.domain.repository.AuthRepository
import com.xquare.domain.repository.GithubRepository
import com.xquare.domain.repository.PointRepository
import com.xquare.domain.repository.WebViewRepository
import com.xquare.domain.repository.attachment.AttachmentRepository
import com.xquare.domain.repository.meal.MealRepository
import com.xquare.domain.repository.schedules.SchedulesRepository
import com.xquare.domain.repository.timetables.TimetablesRepository
import com.xquare.domain.repository.notification.AlarmRepository
import com.xquare.domain.repository.pick.PickRepository
import com.xquare.domain.repository.reports.ReportRepository
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
        mealRepositoryImpl: MealRepositoryImpl,
    ): MealRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    abstract fun bindPointRepository(
        pointRepositoryImpl: PointRepositoryImpl,
    ): PointRepository

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    abstract fun bindWebViewRepository(
        webViewRepositoryImpl: WebViewRepositoryImpl,
    ): WebViewRepository

    @Binds
    abstract fun bindTimetablesRepository(
        timetablesRepositoryImpl: TimetablesRepositoryImpl
    ): TimetablesRepository

    @Binds
    abstract fun bindSchedulesRepository(
        schedulesRepositoryImpl: SchedulesRepositoryImpl
    ): SchedulesRepository

    @Binds
    abstract fun bindAttachmentRepository(
        attachmentRepositoryImpl: AttachmentRepositoryImpl
    ): AttachmentRepository

    @Binds
    abstract fun bindAlarmRepository(
        alarmRepositoryImpl: AlarmRepositoryImpl,
    ): AlarmRepository

    @Binds
    abstract fun bindPickRepository(
        pickRepositoryImpl: PickRepositoryImpl,
    ): PickRepository

    @Binds
    abstract fun bindReportsRepository(
        reportRepositoryImpl: ReportRepositoryImpl,
    ): ReportRepository

    @Binds
    abstract fun bindGithubRepository(
        githubRepositoryImpl: GithubRepositoryImpl
    ): GithubRepository
}