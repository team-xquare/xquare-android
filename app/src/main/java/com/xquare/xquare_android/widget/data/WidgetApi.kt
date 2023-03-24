package com.xquare.xquare_android.widget.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WidgetApi {
    @GET("meals/{date}")
    fun getMeal(
        @Path("date") date: String
    ): Call<MealResponse>
}