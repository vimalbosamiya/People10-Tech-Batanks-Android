package com.batanks.nextplan.forgotpassword

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.dismissKeyboard
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.forgotpassword.viewmodel.AccountRecoveryViewModel
import com.batanks.nextplan.home.markRequiredInRed
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.signing.SigninActivity
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.PasswordLost
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_account_recovery1.*


class AccountRecovery1 :  BaseAppCompatActivity(), View.OnClickListener {

    private val accountRecoveryViewModel: AccountRecoveryViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                AccountRecoveryViewModel(it)
            }
        }).get(AccountRecoveryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_recovery1)

        //loadingDialog = this.getLoadingDialog(0, R.string.submitting_mail_please_wait, theme = R.style.AlertDialogCustom)

        emailTextField.markRequiredInRed()

        accountRecoveryViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    //showLoader()
                }
                Status.SUCCESS -> {

                    //hideLoader()
                    tvPasswordSent.visibility = VISIBLE
                    extFab_back.visibility = VISIBLE
                    extFab_submit.visibility = GONE
                }
                Status.ERROR -> {
                    //hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        backArrow.setOnClickListener(this)
        extFab_submit.setOnClickListener(this)
        extFab_back.setOnClickListener(this)
    }

    private fun isEmailValid(email: CharSequence?): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

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

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.backArrow -> { loadSignInIntent() }
            R.id.extFab_back -> { loadSignInIntent() }

            R.id.extFab_submit -> {

                if (isEmailValid(emailTextField.editText?.text)){

                    val password = PasswordLost(email = emailTextField?.editText?.text.toString())
                    accountRecoveryViewModel.sendMail(password)

                }else {

                    emailTextField.editText?.setError(getString(R.string.valid_email))
                    emailTextField.editText?.requestFocus()
                }
            }
        }
    }

    private fun loadSignInIntent(){

        val intent = Intent(this, SigninActivity::class.java)
        startActivity(intent)
        finish()
    }
}
