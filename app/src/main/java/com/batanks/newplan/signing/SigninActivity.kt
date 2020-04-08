package com.batanks.newplan.signing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.newplan.R
import com.batanks.newplan.arch.BaseContract
import com.batanks.newplan.common.dialogBuilder
import com.batanks.newplan.common.dismissKeyboard
import com.batanks.newplan.common.getLoadingDialog
import com.batanks.newplan.home.HomePlanPreview
import com.batanks.newplan.network.RetrofitClient
import com.batanks.newplan.registration.Registration
import com.batanks.newplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.newplan.arch.response.Status
import com.batanks.newplan.signing.viewmodel.SigninViewModel
import com.batanks.newplan.swagger.api.AuthenticationAPI
import com.batanks.newplan.swagger.model.Login
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity(), BaseContract.BasicLoadingView, View.OnTouchListener, View.OnClickListener {

    private var loadingDialog: AlertDialog? = null
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

        val loginEditTextObservable: Observable<String> = RxTextView.textChanges(loginEditText).skip(1).map { charSequence ->
            charSequence.toString()
        }

        val passwordEditTextObservable: Observable<String> = RxTextView.textChanges(passwordEditText).skip(1).map { charSequence ->
            charSequence.toString()
        }

        observable = Observable.combineLatest(loginEditTextObservable, passwordEditTextObservable, BiFunction<String, String, Boolean> { t1, t2 ->
            /*UserName*/
            val validName = t1.isNotEmpty()
            if (!validName) {
                loginEditText.error = "Password should contain at least 1 letter."
            }
            /*Password*/
            val validPass = t2.isNotEmpty()
            if (!validPass) {
                passwordEditText.error = "Password should contain at least 1 letter."
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

        passwordEyeIcon.setOnTouchListener(this)
        login.setOnClickListener(this)
    }

    override fun showLoader() {
        val progress = loadingDialog
        if (progress != null && !progress.isShowing) {
            progress.show()
        }
    }

    override fun hideLoader() {
        val progress = loadingDialog
        if (!isFinishing && progress != null && progress.isShowing) {
            progress.dismiss()
        }
    }

    override fun handleError(error: Throwable) {

    }

    override fun showMessage(message: String) {
        hideLoader()
        this.dialogBuilder(
                title = message,
                positive = getString(android.R.string.yes),
                negative = getString(android.R.string.no),
                cancelable = false,
                theme = R.style.AlertDialogCustom
        ).create().show()
    }

    override fun showMessage(message: String, title: String, showPositiveButton: Boolean) {}

    override fun context(): Context {
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        hideLoader()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (v?.id) {
            R.id.passwordEyeIcon -> {
                if (passwordEditText.transformationMethod == PasswordTransformationMethod.getInstance()) {
                    passwordEyeIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_on))
                    passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else {
                    passwordEyeIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_off))
                    passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }
        }
        return false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                dismissKeyboard()
                val login = Login(login = loginEditText.text.toString(), password = passwordEditText.text.toString())
                signinViewModel.performSignIn(login)
            }
        }
    }
}