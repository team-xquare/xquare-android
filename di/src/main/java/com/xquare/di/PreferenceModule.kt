package com.xquare.di

import com.xquare.data.local.preference.AuthPreference
import com.xquare.data.local.preference.AuthPreferenceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {

    @Binds
    abstract fun bindAuthPreference(
        authPreferenceImpl: AuthPreferenceImpl
    ): AuthPreference
}