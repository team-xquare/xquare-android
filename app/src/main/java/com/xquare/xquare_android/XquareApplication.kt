package com.xquare.xquare_android

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class XquareApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        FirebaseApp.initializeApp(this)

        val sharedPrefs = getSharedPreferences("token", Context.MODE_PRIVATE)
        // 데이터 모두 삭제
        sharedPrefs.edit().clear().apply()
        saveDeviceToken(this)
        clearCache(this)
        getCode(intent = Intent())
    }
}
