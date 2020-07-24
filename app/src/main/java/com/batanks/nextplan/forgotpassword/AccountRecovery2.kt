package com.batanks.nextplan.forgotpassword

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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
import com.batanks.nextplan.signing.SigninActivity
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.ResetPassword
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_account_recovery2.*

class AccountRecovery2 : BaseAppCompatActivity() {

    private var observable: Observable<Boolean>? = null

    private val accountRecoveryViewModel: AccountRecoveryViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                AccountRecoveryViewModel(it)
            }
        }).get(AccountRecoveryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_recovery2)

        val uri : Uri? = intent.data

        if (uri != null){

            var params : List<String> = uri.pathSegments
            var email : String = params.get(params.size-1)
            var token : String = params.get(params.size-2)

            Toast.makeText(this,"email = " + email + "token = " + token,Toast.LENGTH_LONG).show()
        }

        accountRecoveryViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {

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

        loadingDialog = this.getLoadingDialog(0, R.string.signing_in_please_wait, theme = R.style.AlertDialogCustom)

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

        /*extFab_ok.setOnClickListener {

            if (!TextUtils.isEmpty(newPasswordTextField?.editText?.text.toString()) && !TextUtils.isEmpty(confirmPasswordTextField?.editText?.text.toString())
                && TextUtils.isEmpty(newPasswordTextField?.editText?.text.toString()) == TextUtils.isEmpty(confirmPasswordTextField?.editText?.text.toString())){

                val resetPassword = ResetPassword(password = newPasswordTextField?.editText?.text.toString(),
                        confirmPassword = confirmPasswordTextField?.editText?.text.toString(),
                        email = "",token = "")
                accountRecoveryViewModel.resetPassword(resetPassword)

            }else if (TextUtils.isEmpty(newPasswordTextField?.editText?.text.toString()) && TextUtils.isEmpty(confirmPasswordTextField?.editText?.text.toString())){

                newPasswordTextField.editText?.error = "New Password is Required"
                confirmPasswordTextField.editText?.error = "Confirm Password is Required"

            }else if (TextUtils.isEmpty(newPasswordTextField?.editText?.text.toString())){

                newPasswordTextField.editText?.error = "New Password is Required"

            }else if (TextUtils.isEmpty(confirmPasswordTextField?.editText?.text.toString())){

                confirmPasswordTextField.editText?.error = "New Password is Required"
            }
        }*/
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
