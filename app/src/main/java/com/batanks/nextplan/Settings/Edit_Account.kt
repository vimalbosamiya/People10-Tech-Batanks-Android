package com.batanks.nextplan.Settings

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.User
import kotlinx.android.synthetic.main.activity_edit_account.*

class Edit_Account : AppCompatActivity() {
    //var user_obj : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)

        //input_edit_account_name.setHint(Html.fromHtml(getString(R.string.email_hint)));

        /*if(intent.hasExtra("values")){
           val user_obj : User? = intent.getSerializableExtra("values") as User?
            System.out.println("");
        }*/

        edit_account_cancel.setOnClickListener {

            finish()
        }
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
