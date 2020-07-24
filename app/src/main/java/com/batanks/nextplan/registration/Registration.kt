package com.batanks.nextplan.registration

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
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
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.signing.SigninActivity
import com.batanks.nextplan.signing.viewmodel.RegistrationViewModel
import com.batanks.nextplan.splash.SplashActivity
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.RegisterUser
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.functions.Function7
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.stayLoggedInCheckBox
import java.util.regex.Pattern

class Registration : BaseAppCompatActivity(), View.OnClickListener {

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
        dismissKeyboard()

        registrationViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    Toast.makeText(this, "User account is created", Toast.LENGTH_SHORT).show()
                    hideLoader()
                    /*val intent = Intent(this, HomePlanPreview::class.java)
                    startActivity(intent)*/
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

        val userNameObservable: Observable<String>? = userNameTextField?.editText?.textChanges()?.skip(1)?.map { charSequence ->
            charSequence.toString()
        }
        val firstNameObservable: Observable<String>? = firstNameTextField?.editText?.textChanges()?.skip(1)?.map { charSequence ->
            charSequence.toString()
        }
        val lastNameObservable: Observable<String>? = lastNameTextField?.editText?.textChanges()?.skip(1)?.map { charSequence ->
            charSequence.toString()
        }
        val emailObservable: Observable<String>? = emailTextField?.editText?.textChanges()?.skip(1)?.map { charSequence ->
            charSequence.toString()
        }
        val passwordEditTextObservable: Observable<String>? = passwordTextField?.editText?.textChanges()?.skip(1)?.map { charSequence ->
            charSequence.toString()
        }
        val confirmPasswordEditTextObservable: Observable<String>? = confirmPasswordTextField?.editText?.textChanges()?.skip(1)?.map { charSequence ->
            charSequence.toString()
        }
        val phoneNumberObservable: Observable<String>? = phoneNumberTextField?.editText?.textChanges()?.skip(1)?.map { charSequence ->
            charSequence.toString()
        }

        ccp.registerCarrierNumberEditText(phoneNumberTextField?.editText)

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
                    val validUserName = s1.length > 6
                    if (!validUserName) {
                        userNameTextField?.editText?.error = "UserName should contain at least 6 letter."
                    }
                    /*FirstName*/
                    val validFirstName = s2.isNotEmpty()
                    if (!validFirstName) {
                        firstNameTextField?.editText?.error = "FirstName should contain at least 1 letter."
                    }
                    /*LastName*/
                    val validLastName = s3.isNotEmpty()
                    if (!validLastName) {
                        lastNameTextField?.editText?.error = "LastName should contain at least 1 letter."
                    }
                    /*EmailID*/
                    val validEmail = s4.isNotEmpty() && isEmailValid(s4)
                    if (!validEmail) {
                        emailTextField?.editText?.error = "Enter valid email id."
                    }
                    /*Password*/
                    val validPass = s5.isNotEmpty() && isValidPassword(s5)
                    if (!validPass) {
                        passwordTextField?.editText?.error = "Password should be between 6 to 128 characters and should contain at least 1 lower case and 1 upper case letter and 1 digit."
                    }
                    /*Password & ConfirmPassword*/
                    val samePassword = s5.equals(s6, false)
                    if (!samePassword) {
                        confirmPasswordTextField?.editText?.error = "Password field & Confirm Password are different."
                    }
                    /*ConfirmPassword*/
                    val validConfirmPass = s6.isNotEmpty() && isValidPassword(s6)
                    if (!validConfirmPass) {
                        confirmPasswordTextField?.editText?.error = "Password should be between 6 to 128 characters and should contain at least 1 lower case and 1 upper case letter and 1 digit."
                    }
                    /*PhoneNumber*/
                    val validPhone = s7.isNotEmpty() && ccp.isValidFullNumber
                    if (!validPhone) {
                        phoneNumberTextField?.editText?.error = "Please enter a vaild phone number."
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
                /*val intent = Intent(this, SigninActivity::class.java)
                startActivity(intent)*/
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
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


    override fun onClick(v: View?) {
        dismissKeyboard()
        when (v?.id) {
            R.id.signIn -> {
                /*val validUserName = userNameEditText.length() > 6
                if (userNameEditText.length() > 6) {
                    Toast.makeText(this,"sign in Clicked",Toast.LENGTH_SHORT).show()

                }else {

                    userNameTextField?.editText?.error = "UserName should contain at least 6 letter."
                }*/
                //Toast.makeText(this,"sign in Clicked",Toast.LENGTH_SHORT).show()
                showLoader()
                RetrofitClient.cookieJar?.clear()
                getSharedPreferences(SplashActivity.PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(SplashActivity.PREF_NAME, stayLoggedInCheckBox.isChecked).apply()
                val user = RegisterUser(
                        username = userNameTextField?.editText?.text.toString(),
                        first_name = firstNameTextField?.editText?.text.toString(),
                        last_name = lastNameTextField?.editText?.text.toString(),
                        email = emailTextField?.editText?.text.toString(),
                        password1 = passwordTextField?.editText?.text.toString(),
                        password2 = confirmPasswordTextField?.editText?.text.toString(),
                        phone_number = ccp.selectedCountryCodeWithPlus + phoneNumberTextField?.editText?.text.toString()
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