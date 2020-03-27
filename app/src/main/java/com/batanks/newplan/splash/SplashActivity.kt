package com.batanks.newplan.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.batanks.newplan.R
import com.batanks.newplan.signin.SigninActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, SigninActivity::class.java))
            finish()
        }, SPLASH_SCREEN_TIMEOUT)
    }

    companion object {
        const val SPLASH_SCREEN_TIMEOUT: Long = 3 * 1000 //times in milliseconds
    }
}