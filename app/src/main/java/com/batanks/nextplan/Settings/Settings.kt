package com.batanks.nextplan.Settings

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.batanks.nextplan.R
import com.batanks.nextplan.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_signin.*

class Settings : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        img_settings_close.setOnClickListener {

           finish()
        }

        rl_settings_account.setOnClickListener(View.OnClickListener {
            intent = Intent(this, Account :: class.java)
            startActivity(intent)
        })
        rl_settings_contacts.setOnClickListener(View.OnClickListener {
            intent = Intent(this, Contact :: class.java)
            startActivity(intent)
        })
        rl_settings_followup.setOnClickListener(View.OnClickListener {
            intent = Intent(this, Followups :: class.java)
            startActivity(intent)
        })
        rl_settings_logout.setOnClickListener(View.OnClickListener {
            getSharedPreferences(SplashActivity.PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply()
            intent = Intent(this, SplashActivity :: class.java)
            startActivity(intent)
            finishAffinity()
        })
    }
}
