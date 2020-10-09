package com.batanks.nextplan.forgotpassword

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import androidx.lifecycle.Observer
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.forgotpassword.viewmodel.AccountRecoveryViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.registration.Registration
import com.batanks.nextplan.signing.SigninActivity
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.ResetPassword
import com.batanks.nextplan.swagger.model.mode.ResetPasswordConfirm
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_account_recovery2.*
import java.util.regex.Pattern

class AccountRecovery2 : BaseAppCompatActivity() {

    private var observable: Observable<Boolean>? = null

    private val accountRecoveryViewModel: AccountRecoveryViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                AccountRecoveryViewModel(it)
            }
        }).get(AccountRecoveryViewModel::class.java)
    }

    //var token : String? = null

    lateinit var token : String
    lateinit var email : String

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_recovery2)

        val uri : Uri? = intent.data

        if (uri != null){

            token = uri.getQueryParameter("token").toString()
            email = uri.getQueryParameter("email").toString()
            //var email : String? = uri.getQueryParameter("email")

            Log.d("token",token)
            Log.d("email",email)

            /*var params : List<String> = uri.pathSegments
            var link : String = uri.toString()
            Toast.makeText(this, link ,Toast.LENGTH_LONG).show()
            Log.d("Link",link)

            for(p in params){

                println(p)
                Log.d("param",uri.pathSegments.toString())
                Log.d("params",params.toString())
            }
            var email : String = params.get(params.size-1)
            var token : String = params.get(params.size-2)
            Toast.makeText(this,"email = " + email + "token = " + token,Toast.LENGTH_LONG).show()*/
        }

        accountRecoveryViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {

                    Toast.makeText(this,"Paswword Changed Successfully",Toast.LENGTH_LONG).show()

                    val intent = Intent(this, SigninActivity::class.java)
                    startActivity(intent)
                    finish()

                    hideLoader()
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        loadingDialog = this.getLoadingDialog(0, R.string.changing_password_please_wait, theme = R.style.AlertDialogCustom)

        backArrow.setOnClickListener {

            val intent = Intent(this, AccountRecovery1::class.java)
            startActivity(intent)
            finish()
        }

        cancelButton.setOnClickListener {

            val intent = Intent(this, AccountRecovery1::class.java)
            startActivity(intent)
            finish()
        }

        extFab_ok.setOnClickListener {

            /*if (isValidPassword(newPasswordTextField.editText?.text.toString()) && isValidPassword(confirmPasswordTextField.editText?.text.toString()) &&
                    newPasswordTextField.editText?.text.toString() == confirmPasswordTextField.editText?.text.toString()){

                val resetPassword = ResetPasswordConfirm(password1 = newPasswordTextField?.editText?.text.toString(),
                        password2 = confirmPasswordTextField?.editText?.text.toString(),
                        email = email, token = token)
                //Log.d("token from listener",token +" "+ email +" "+ newPasswordTextField?.editText?.text.toString() +" "+ confirmPasswordTextField?.editText?.text.toString())
                //println("token from listener" +" "+ token +" "+ email +" "+newPasswordTextField?.editText?.text.toString() +" "+ confirmPasswordTextField?.editText?.text.toString() )

                accountRecoveryViewModel.resetPassword(resetPassword)

            }else if (!isValidPassword(newPasswordTextField.editText?.text.toString())){

                newPasswordTextField.editText?.error = "Paswword must contain atleast 1 lower case and 1 upeer case letter and 1 number and 1 special charatcer with no spaces"
                newPasswordTextField.editText?.requestFocus()

            } else if (newPasswordTextField.editText?.text.toString() != confirmPasswordTextField.editText?.text.toString()){

                confirmPasswordTextField.editText?.error = "Password doesn't match"
                confirmPasswordTextField.editText?.requestFocus()

            } else if (TextUtils.isEmpty(newPasswordTextField?.editText?.text.toString()) && TextUtils.isEmpty(confirmPasswordTextField?.editText?.text.toString())){

                newPasswordTextField.editText?.error = "New Password is Required"
                confirmPasswordTextField.editText?.error = "Confirm Password is Required"

                newPasswordTextField.editText?.requestFocus()
                confirmPasswordTextField.editText?.requestFocus()

            } else if (TextUtils.isEmpty(newPasswordTextField?.editText?.text.toString())){

                newPasswordTextField.editText?.error = "New Password is Required"
                newPasswordTextField.editText?.requestFocus()

            }  else if (TextUtils.isEmpty(confirmPasswordTextField?.editText?.text.toString())){

                confirmPasswordTextField.editText?.error = "New Password is Required"
                confirmPasswordTextField.editText?.requestFocus()

            }else if (TextUtils.isEmpty(newPasswordTextField?.editText?.text.toString()) != TextUtils.isEmpty(confirmPasswordTextField?.editText?.text.toString())){

                confirmPasswordTextField.editText?.error = "Password doesn't match"
                confirmPasswordTextField.editText?.requestFocus()
            }*/

            if (isValidPassword(newPasswordTextField.editText?.text?.toString()) && newPasswordTextField.editText?.length()!! >= 6){

                if (newPasswordTextField.editText!!.text?.toString() == confirmPasswordTextField.editText?.text.toString()){

                    val resetPassword = ResetPasswordConfirm(password1 = newPasswordTextField?.editText?.text.toString(),
                            password2 = confirmPasswordTextField?.editText?.text.toString(),
                            email = email, token = token)
                    //Log.d("token from listener",token +" "+ email +" "+ newPasswordTextField?.editText?.text.toString() +" "+ confirmPasswordTextField?.editText?.text.toString())
                    //println("token from listener" +" "+ token +" "+ email +" "+newPasswordTextField?.editText?.text.toString() +" "+ confirmPasswordTextField?.editText?.text.toString() )

                    accountRecoveryViewModel.resetPassword(resetPassword)

                } else {

                    confirmPasswordTextField.editText?.error = "New password and Confirm password doesn't match"
                    confirmPasswordTextField.editText?.requestFocus()
                }

            } else {

                newPasswordTextField.editText?.error = "Paswword should contain atleast 6 characters with 1 lower case and 1 upeer case letter and 1 number and 1 special charatcer with no spaces"
                newPasswordTextField.editText?.requestFocus()
            }


            /*if (!TextUtils.isEmpty(newPasswordTextField?.editText?.text.toString()) && !TextUtils.isEmpty(confirmPasswordTextField?.editText?.text.toString())
                && TextUtils.isEmpty(newPasswordTextField?.editText?.text.toString()) == TextUtils.isEmpty(confirmPasswordTextField?.editText?.text.toString())){

                val resetPassword = ResetPasswordConfirm(password1 = newPasswordTextField?.editText?.text.toString(),
                        password2 = confirmPasswordTextField?.editText?.text.toString(),
                        email = email, token = token)
                //Log.d("token from listener",token +" "+ email +" "+ newPasswordTextField?.editText?.text.toString() +" "+ confirmPasswordTextField?.editText?.text.toString())
                //println("token from listener" +" "+ token +" "+ email +" "+newPasswordTextField?.editText?.text.toString() +" "+ confirmPasswordTextField?.editText?.text.toString() )

                accountRecoveryViewModel.resetPassword(resetPassword)

            }
            else if (TextUtils.isEmpty(newPasswordTextField?.editText?.text.toString()) && TextUtils.isEmpty(confirmPasswordTextField?.editText?.text.toString())){

                newPasswordTextField.editText?.error = "New Password is Required"
                confirmPasswordTextField.editText?.error = "Confirm Password is Required"

                newPasswordTextField.editText?.requestFocus()
                confirmPasswordTextField.editText?.requestFocus()

            }
            else if (TextUtils.isEmpty(newPasswordTextField?.editText?.text.toString())){

                newPasswordTextField.editText?.error = "New Password is Required"
                newPasswordTextField.editText?.requestFocus()

            }
            else if (TextUtils.isEmpty(confirmPasswordTextField?.editText?.text.toString())){

                confirmPasswordTextField.editText?.error = "New Password is Required"
                confirmPasswordTextField.editText?.requestFocus()

            }
            else if (TextUtils.isEmpty(newPasswordTextField?.editText?.text.toString()) != TextUtils.isEmpty(confirmPasswordTextField?.editText?.text.toString())){

                confirmPasswordTextField.editText?.error = "Password doesn't match"
                confirmPasswordTextField.editText?.requestFocus()
            }*/
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

    private fun isValidPassword(textToCheck: String?): Boolean = textPattern.matcher(textToCheck).matches()

    companion object {
        //val textPattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])$")
        val textPattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+$)(?=.*\\d).+$")

        //(?=.*\d).+  (?=.*[@#\$%^&+=])
    }
}
