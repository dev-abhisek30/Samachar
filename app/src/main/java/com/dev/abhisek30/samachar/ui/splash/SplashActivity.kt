package com.dev.abhisek30.samachar.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dev.abhisek30.samachar.R
import com.dev.abhisek30.samachar.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
        }, 2000)

        /*Timer().schedule(2000) {
            startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
        }*/
    }
}