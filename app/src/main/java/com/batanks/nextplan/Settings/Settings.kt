package com.batanks.nextplan.Settings

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_signin.*

class Settings : BaseAppCompatActivity() {


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
            //RetrofitClient.cookieJar?.clear()
            getSharedPreferences(SplashActivity.PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply()
            intent = Intent(this, SplashActivity :: class.java)
            startActivity(intent)
            finishAffinity()
        })
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = getCurrentFocus()
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm?.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
