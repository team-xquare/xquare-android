package com.xquare.xquare_android.widget.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WidgetRetrofitBuilder {
    private val baseURI = "https://api.xquare.app/"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseURI)
        .client(okHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun widgetApi(): WidgetApi =
        retrofit.create(WidgetApi::class.java)
}

private fun okHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
}