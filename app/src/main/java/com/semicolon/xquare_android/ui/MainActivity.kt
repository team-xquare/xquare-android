package com.semicolon.xquare_android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.semicolon.xquare_android.ui.theme.XquareandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XquareandroidTheme {
                BaseApp()
            }
        }
    }
}