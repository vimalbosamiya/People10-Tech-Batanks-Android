package com.batanks.nextplan.signing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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
                    showMessage(response.error?.message.toString())
                }
            }
        })

        loadingDialog = this.getLoadingDialog(0, R.string.signing_in_please_wait, theme = R.style.AlertDialogCustom)

        createAccount.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
            finish()
        }

        val loginEditTextObservable: Observable<String>? = loginTextField?.editText?.textChanges()?.skip(1)?.map { charSequence ->
            charSequence.toString()
        }
        val passwordEditTextObservable: Observable<String>? = passTextField?.editText?.textChanges()?.skip(1)?.map { charSequence ->
            charSequence.toString()
        }

        observable = Observable.combineLatest(loginEditTextObservable, passwordEditTextObservable, BiFunction<String, String, Boolean> { t1, t2 ->
            /*UserName*/
            val validName = t1.isNotEmpty()
            if (!validName) {
                loginTextField?.editText?.error = "Password should contain at least 1 letter."
            }
            /*Password*/
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
        })

        login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                dismissKeyboard()
                RetrofitClient.cookieJar?.clear()
                getSharedPreferences(SplashActivity.PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(SplashActivity.PREF_NAME, stayLoggedInCheckBox.isChecked).apply()
                val login = Login(login = loginTextField?.editText?.text.toString(), password = passTextField?.editText?.text.toString())
                signinViewModel.performSignIn(login)
            }
        }
    }
}