package com.batanks.nextplan.Settings

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.batanks.nextplan.R
import com.batanks.nextplan.home.ModelPreferencesManager
import com.batanks.nextplan.swagger.model.User
import kotlinx.android.synthetic.main.activity_edit_account.*
import java.util.regex.Pattern

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

        val userData = ModelPreferencesManager.get<User>("USER_DATA")

        edit_account_cancel.setOnClickListener {

            finish()
        }

        edit_account_ok.setOnClickListener {

            if (isValidUsername(tip_edit_account_name.editText?.text.toString()) && tip_edit_account_name.editText?.length()!! >= 4){

                if (isEmailValid(tip_edit_account_email.editText?.text)){

                    if(TextUtils.isEmpty(tip_edit_account_phone_no.editText?.text.toString()) || tip_edit_account_phone_no.editText?.length()!! >= 4 && isValidPhoneNumber(tip_edit_account_phone_no.editText?.text.toString())){


                        finish()

                    } else {

                        tip_edit_account_phone_no.editText?.error = "Mobile number should contain atleast 4 numbers with no spaces"
                        tip_edit_account_phone_no.editText?.requestFocus()
                    }

                } else {

                    tip_edit_account_email.editText?.error = "Enter a valid email address"
                    tip_edit_account_email.editText?.requestFocus()
                }

            } else {

                tip_edit_account_name.editText?.error = "Username should contain atleast 4 characters with no spaces"
                tip_edit_account_name.editText?.requestFocus()
            }
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

    private fun isEmailValid(email: CharSequence?): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidUsername(textToCheck: String?) : Boolean = userNamePattern.matcher(textToCheck).matches()
    private fun isValidPhoneNumber(textToCheck: String?) : Boolean = PhoneNumberPattern.matcher(textToCheck).matches()

    companion object {

        val userNamePattern: Pattern = Pattern.compile("^(?=\\S+\$).+$")
        val PhoneNumberPattern: Pattern = Pattern.compile("^(?=\\S+\$).+$")
    }
}
