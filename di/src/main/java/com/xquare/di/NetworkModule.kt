package com.xquare.di

import com.xquare.data.interceptor.AuthorizationInterceptor
import com.xquare.data.remote.api.AuthApi
import com.xquare.data.remote.api.MealApi
import com.xquare.data.remote.api.ProfileApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.xquare.app/"

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideOkHttpclient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(authorizationInterceptor)
        .build()

    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideMealApi(
        retrofit: Retrofit
    ): MealApi =
        retrofit.create(MealApi::class.java)

    @Provides
    fun provideAuthApi(
        retrofit: Retrofit
    ): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    fun provideProfileApi(
        retrofit: Retrofit
    ): ProfileApi =
        retrofit.create(ProfileApi::class.java)
}