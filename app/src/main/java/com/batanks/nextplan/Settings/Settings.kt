package com.batanks.nextplan.Settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.batanks.nextplan.R
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        rl_settings_account.setOnClickListener(View.OnClickListener {
            intent = Intent(this, Account :: class.java)
            startActivity(intent)
        })
    }
}
