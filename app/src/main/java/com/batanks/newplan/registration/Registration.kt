package com.batanks.newplan.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
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
import com.batanks.newplan.arch.response.Status
import com.batanks.newplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.newplan.home.HomePlanPreview
import com.batanks.newplan.network.RetrofitClient
import com.batanks.newplan.signing.SigninActivity
import com.batanks.newplan.signing.viewmodel.RegistrationViewModel
import com.batanks.newplan.splash.SplashActivity
import com.batanks.newplan.swagger.api.AuthenticationAPI
import com.batanks.newplan.swagger.model.RegisterUser
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.Function7
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.passwordEditText
import kotlinx.android.synthetic.main.activity_registration.passwordEyeIcon
import kotlinx.android.synthetic.main.activity_registration.stayLoggedInCheckBox
import kotlinx.android.synthetic.main.activity_signin.*
import java.util.regex.Pattern

class Registration : AppCompatActivity(), BaseContract.BasicLoadingView, View.OnTouchListener, View.OnClickListener {

    private var loadingDialog: AlertDialog? = null
    private var observable: Observable<Boolean>? = null

    private val registrationViewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                RegistrationViewModel(it)
            }
        }).get(RegistrationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.registration)

        registrationViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    Toast.makeText(this, "User account is created", Toast.LENGTH_SHORT).show()
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

        loadingDialog = this.getLoadingDialog(0, R.string.creating_user_please_wait, theme = R.style.AlertDialogCustom)

        signIn.setOnClickListener(this)

        passwordEyeIcon.setOnTouchListener(this)
        confirmPasswordEyeIcon.setOnTouchListener(this)

        val userNameObservable: Observable<String> = RxTextView.textChanges(userName).skip(1).map { charSequence ->
            charSequence.toString()
        }
        val firstNameObservable: Observable<String> = RxTextView.textChanges(firstName).skip(1).map { charSequence ->
            charSequence.toString()
        }
        val lastNameObservable: Observable<String> = RxTextView.textChanges(lastName).skip(1).map { charSequence ->
            charSequence.toString()
        }
        val emailObservable: Observable<String> = RxTextView.textChanges(email).skip(1).map { charSequence ->
            charSequence.toString()
        }
        val passwordEditTextObservable: Observable<String> = RxTextView.textChanges(passwordEditText).skip(1).map { charSequence ->
            charSequence.toString()
        }
        val confirmPasswordEditTextObservable: Observable<String> = RxTextView.textChanges(confirmPasswordEditText).skip(1).map { charSequence ->
            charSequence.toString()
        }
        val phoneNumberObservable: Observable<String> = RxTextView.textChanges(phoneNumber).skip(1).map { charSequence ->
            charSequence.toString()
        }

        ccp.registerCarrierNumberEditText(phoneNumber)

        observable = Observable.combineLatest(
                userNameObservable,
                firstNameObservable,
                lastNameObservable,
                emailObservable,
                passwordEditTextObservable,
                confirmPasswordEditTextObservable,
                phoneNumberObservable,
                Function7<String?, String?, String?, String?, String?, String?, String?, Boolean?> { s1, s2, s3, s4, s5, s6, s7 ->
                    /*UserName*/
                    val validUserName = s1.isNotEmpty()
                    if (!validUserName) {
                        userName.error = "UserName should contain at least 1 letter."
                    }
                    /*FirstName*/
                    val validFirstName = s2.isNotEmpty()
                    if (!validFirstName) {
                        firstName.error = "FirstName should contain at least 1 letter."
                    }
                    /*LastName*/
                    val validLastName = s3.isNotEmpty()
                    if (!validLastName) {
                        lastName.error = "LastName should contain at least 1 letter."
                    }
                    /*EmailID*/
                    val validEmail = s4.isNotEmpty() && isEmailValid(s4)
                    if (!validEmail) {
                        email.error = "Enter valid email id."
                    }
                    /*Password*/
                    val validPass = s5.isNotEmpty() && isValidPassword(s5)
                    if (!validPass) {
                        passwordEditText.error = "Password should contain at least 1 lower case and 1 upper case letter and 1 digit."
                    }
                    /*Password & ConfirmPassword*/
                    val samePassword = s5.equals(s6, false)
                    if (!samePassword) {
                        confirmPasswordEditText.error = "Password field & Confirm Password are different."
                    }
                    /*ConfirmPassword*/
                    val validConfirmPass = s6.isNotEmpty() && isValidPassword(s6)
                    if (!validConfirmPass) {
                        confirmPasswordEditText.error = "Password should contain at least 1 lower case and 1 upper case letter and 1 digit."
                    }
                    /*PhoneNumber*/
                    val validPhone = s7.isNotEmpty() && ccp.isValidFullNumber
                    if (!validPhone) {
                        phoneNumber.error = "Please enter a vaild phone number."
                    }

                    validUserName && validFirstName && validLastName && validEmail && validPass && validConfirmPass && samePassword && validPhone
                })

        observable?.subscribe(object : DisposableObserver<Boolean>() {

            override fun onNext(validFlag: Boolean) {
                signIn.isEnabled = validFlag
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, SigninActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
        hideLoader()
    }

    override fun showMessage(message: String) {
        hideLoader()
        this.dialogBuilder(
                title = message,
                positive = getString(android.R.string.yes),
                negative = getString(android.R.string.no),
                cancelable = false,
                theme = R.style.AlertDialogCustom_Register
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
            R.id.confirmPasswordEyeIcon -> {
                if (confirmPasswordEditText.transformationMethod == PasswordTransformationMethod.getInstance()) {
                    confirmPasswordEyeIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_on))
                    confirmPasswordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else {
                    confirmPasswordEyeIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_off))
                    confirmPasswordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }
        }
        return false
    }

    override fun onClick(v: View?) {
        dismissKeyboard()
        when (v?.id) {
            R.id.signIn -> {
                showLoader()
                RetrofitClient.cookieJar?.clear()
                getSharedPreferences(SplashActivity.PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(SplashActivity.PREF_NAME, stayLoggedInCheckBox.isChecked).apply()
                val user = RegisterUser(
                        username = userName.text.toString(),
                        first_name = firstName.text.toString(),
                        last_name = lastName.text.toString(),
                        email = email.text.toString(),
                        password1 = passwordEditText.text.toString(),
                        password2 = confirmPasswordEditText.text.toString(),
                        phone_number = ccp.selectedCountryCodeWithPlus + phoneNumber.text.toString()
                )
                registrationViewModel.createUser(user)
            }
        }
    }

    private fun isEmailValid(email: CharSequence?): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPassword(textToCheck: String?): Boolean = textPattern.matcher(textToCheck).matches()

    companion object {
        val textPattern: Pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")
    }
}