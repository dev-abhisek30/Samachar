package com.dev.abhisek30.samachar.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dev.abhisek30.samachar.BuildConfig
import com.dev.abhisek30.samachar.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        val t = BuildConfig.API_KEY
        setContentView(R.layout.activity_splash)
    }
}