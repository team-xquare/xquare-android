package com.xquare.di

import com.xquare.data.repository.AuthRepositoryImpl
import com.xquare.data.repository.PointRepositoryImpl
import com.xquare.data.repository.WebViewRepositoryImpl
import com.xquare.data.repository.attachment.AttachmentRepositoryImpl
import com.xquare.data.repository.meal.MealRepositoryImpl
import com.xquare.data.repository.user.UserRepositoryImpl
import com.xquare.domain.repository.AuthRepository
import com.xquare.domain.repository.PointRepository
import com.xquare.domain.repository.WebViewRepository
import com.xquare.domain.repository.attachment.AttachmentRepository
import com.xquare.domain.repository.meal.MealRepository
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
    abstract fun bindSimplePointRepository(
        pointRepositoryImpl: com.xquare.data.repository.point.PointRepositoryImpl,
    ): com.xquare.domain.repository.point.PointRepository

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    abstract fun bindWebViewRepository(
        webViewRepositoryImpl: WebViewRepositoryImpl,
    ): WebViewRepository

    @Binds
    abstract fun bindAttachmentRepository(
        attachmentRepositoryImpl: AttachmentRepositoryImpl
    ): AttachmentRepository
}