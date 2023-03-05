package com.xquare.di

import com.xquare.data.AppCookieManagerImpl
import com.xquare.domain.AppCookieManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CookieManagerModule {

    @Binds
    abstract fun bindAppCookieManager(
        appCookieManagerImpl: AppCookieManagerImpl,
    ): AppCookieManager
}