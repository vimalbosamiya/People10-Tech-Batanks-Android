package com.batanks.nextplan.forgotpassword

import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.dismissKeyboard
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.forgotpassword.viewmodel.AccountRecoveryViewModel
import com.batanks.nextplan.home.isValidPassword
import com.batanks.nextplan.home.markRequiredInRed
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.registration.Registration
import com.batanks.nextplan.signing.SigninActivity
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.ResetPassword
import com.batanks.nextplan.swagger.model.mode.ResetPasswordConfirm
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_account_recovery2.*
import java.util.regex.Pattern

class AccountRecovery2 : BaseAppCompatActivity(), View.OnClickListener {

    private val accountRecoveryViewModel: AccountRecoveryViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                AccountRecoveryViewModel(it)
            }
        }).get(AccountRecoveryViewModel::class.java)
    }

    lateinit var token : String
    lateinit var email : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_recovery2)

        //loadingDialog = this.getLoadingDialog(0, R.string.changing_password_please_wait, theme = R.style.AlertDialogCustom)

        newPasswordTextField.markRequiredInRed()
        confirmPasswordTextField.markRequiredInRed()

        val uri : Uri? = intent.data

        if (uri != null){

            token = uri.getQueryParameter("token").toString()
            email = uri.getQueryParameter("email").toString()
        }

        accountRecoveryViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    //showLoader()
                }
                Status.SUCCESS -> {

                    Toast.makeText(this,getString(R.string.password_changed_successfully),Toast.LENGTH_LONG).show()
                    val intent = Intent(this, SigninActivity::class.java)
                    startActivity(intent)
                    finish()
                    //hideLoader()
                }
                Status.ERROR -> {
                    //hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        backArrow.setOnClickListener(this)
        cancelButton.setOnClickListener(this)
        extFab_ok.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        dismissKeyboard()

        when (v?.id) {

            R.id.backArrow -> { loadAccountRecovery1Intent() }
            R.id.cancelButton -> { loadAccountRecovery1Intent() }

            R.id.extFab_ok -> {

                if (isValidPassword(newPasswordTextField.editText?.text?.toString()) && newPasswordTextField.editText?.length()!! >= 6){

                    println(isValidPassword(newPasswordTextField.editText?.text?.toString()))

                    if (newPasswordTextField.editText!!.text?.toString() == confirmPasswordTextField.editText?.text.toString()){

                        val resetPassword = ResetPasswordConfirm(password1 = newPasswordTextField?.editText?.text.toString(),
                                password2 = confirmPasswordTextField?.editText?.text.toString(),
                                email = email, token = token)

                        accountRecoveryViewModel.resetPassword(resetPassword)

                    } else {

                        confirmPasswordTextField.editText?.setError(getString(R.string.password_doesnt_match))
                        confirmPasswordTextField.editText?.requestFocus()
                    }
                } else {

                    newPasswordTextField.editText?.setError(getString(R.string.password_condition))
                    newPasswordTextField.editText?.requestFocus()
                }
            }
        }

    }

    private fun loadAccountRecovery1Intent(){

        val intent = Intent(this, AccountRecovery1::class.java)
        startActivity(intent)
        finish()
    }

    companion object {

        val textPattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#?!@$%^&()\\[\\\\\\]<>*~:_/|`-])(?=\\S+$)(?=.*\\d).+$")
        //val textPattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!/*-_])(?=\\S+$)(?=.*\\d).+$")
        //val textPattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!/*-_])(?=\\S+$)(?=.*\\d).+$")
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
