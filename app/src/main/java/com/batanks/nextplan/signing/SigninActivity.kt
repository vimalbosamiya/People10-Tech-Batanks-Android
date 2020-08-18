package com.batanks.nextplan.signing

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.common.dismissKeyboard
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.registration.Registration
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.forgotpassword.AccountRecovery1
import com.batanks.nextplan.signing.viewmodel.SigninViewModel
import com.batanks.nextplan.splash.SplashActivity
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.Login
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : BaseAppCompatActivity(), View.OnClickListener {

    private var observable: Observable<Boolean>? = null

    private val signinViewModel: SigninViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                SigninViewModel(it)
            }
        }).get(SigninViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        dismissKeyboard()

        forgotPassword.setOnClickListener {

            val intent = Intent(this, AccountRecovery1::class.java)
            startActivity(intent)
            finish()
        }

        signinViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()
                    val intent = Intent(this, HomePlanPreview::class.java)
                    startActivity(intent)
                    finish()
                }
                Status.ERROR -> {
                    hideLoader()
                   // showMessage(response.error?.message.toString())
                    Toast.makeText(this,"Username or Password is incorrect",Toast.LENGTH_LONG).show()
                }
            }
        })

      loadingDialog = this.getLoadingDialog(0, R.string.signing_in_please_wait, theme = R.style.AlertDialogCustom)

        createAccount.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
            //finish()
        }

        /*val loginEditTextObservable: Observable<String>? = loginTextField?.editText?.textChanges()?.skip(1)?.map { charSequence ->
            charSequence.toString()
        }
        val passwordEditTextObservable: Observable<String>? = passTextField?.editText?.textChanges()?.skip(1)?.map { charSequence ->
            charSequence.toString()
        }

        observable = Observable.combineLatest(loginEditTextObservable, passwordEditTextObservable, BiFunction<String, String, Boolean> { t1, t2 ->
            *//*UserName*//*
            val validName = t1.isNotEmpty()
            if (!validName) {
                loginTextField?.editText?.error = "Password should contain at least 1 letter."
            }
            *//*Password*//*
            val validPass = t2.isNotEmpty()
            if (!validPass) {
                passTextField?.editText?.error = "Password should contain at least 1 letter."
            }
            validName && validPass
        })

        observable?.subscribe(object : DisposableObserver<Boolean?>() {

            override fun onNext(validFlag: Boolean) {
                login.isEnabled = validFlag
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        })*/

        login.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                //Toast.makeText(this,"working",Toast.LENGTH_SHORT).show()

                if (!TextUtils.isEmpty(loginTextField?.editText?.text.toString()) && !TextUtils.isEmpty(passTextField?.editText?.text.toString())){

                    dismissKeyboard()
                    RetrofitClient.cookieJar?.clear()
                    getSharedPreferences(SplashActivity.PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(SplashActivity.PREF_NAME, stayLoggedInCheckBox.isChecked).apply()
                    val login = Login(login = loginTextField?.editText?.text.toString(), password = passTextField?.editText?.text.toString())
                    signinViewModel.performSignIn(login)

                } else if (TextUtils.isEmpty(loginTextField?.editText?.text.toString()) && TextUtils.isEmpty(passTextField?.editText?.text.toString())){

                    loginTextField.editText?.error = "Username is Required"
                    passTextField.editText?.error = "Password is Required"

                    loginTextField.editText?.requestFocus()
                    passTextField.editText?.requestFocus()

                } else if (TextUtils.isEmpty(passTextField?.editText?.text.toString())){

                    passTextField.editText?.error = "Password is Required"
                    passTextField.editText?.requestFocus()

                } else if (TextUtils.isEmpty(loginTextField?.editText?.text.toString())){

                    loginTextField.editText?.error = "Username is Required"
                    loginTextField.editText?.requestFocus()
                }
            }
        }
    }
}