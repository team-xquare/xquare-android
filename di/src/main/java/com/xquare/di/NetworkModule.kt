package com.xquare.di

import com.xquare.data.interceptor.AuthorizationInterceptor
import com.xquare.data.interceptor.EmptyBodyInterceptor
import com.xquare.data.remote.FileUploadManager
import com.xquare.data.remote.api.*
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

    private const val BASE_URL = "https://prod-server.xquare.app/"

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideOkHttpclient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor,
        emptyBodyInterceptor: EmptyBodyInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(authorizationInterceptor)
        .addInterceptor(emptyBodyInterceptor)
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

    @Provides
    fun providePointApi(
        retrofit: Retrofit
    ): PointApi =
        retrofit.create(PointApi::class.java)

    @Provides
    fun provideUserApi(
        retrofit: Retrofit
    ): UserApi =
        retrofit.create(UserApi::class.java)

    @Provides
    fun provideAttachmentApi(
        retrofit: Retrofit
    ): AttachmentApi =
        retrofit.create(AttachmentApi::class.java)

    @Provides
    fun provideTimetablesApi(
        retrofit: Retrofit
    ): TimetablesApi =
        retrofit.create(TimetablesApi::class.java)

    @Provides
    fun provideSchedulesApi(
        retrofit: Retrofit
    ): SchedulesApi =
        retrofit.create(SchedulesApi::class.java)

    @Provides
    fun provideNotificationApi(
        retrofit: Retrofit
    ): NotificationApi =
        retrofit.create(NotificationApi::class.java)

    @Provides
    fun providePickApi(
        retrofit: Retrofit
    ): PickApi =
        retrofit.create(PickApi::class.java)

    @Provides
    fun provideReportsApi(
        retrofit: Retrofit
    ): ReportsApi =
        retrofit.create(ReportsApi::class.java)

    @Provides
    fun provideGithubApi(
        retrofit: Retrofit
    ): GithubApi =
        retrofit.create((GithubApi::class.java))

    @Provides
    fun provideFileUploadManager(
        attachmentApi: AttachmentApi,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): FileUploadManager = FileUploadManager(
        attachmentApi = attachmentApi,
        httpLoggingInterceptor = httpLoggingInterceptor,
    )
}