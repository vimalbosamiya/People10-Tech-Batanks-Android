package com.batanks.newplan.signing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.batanks.newplan.R
import com.batanks.newplan.registration.Registration
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        createAccount.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
            finish()
        }
    }
}