package com.xquare.xquare_android

import android.app.Application
import com.google.firebase.FirebaseApp
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class XquareApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        FirebaseApp.initializeApp(this)
    }
}