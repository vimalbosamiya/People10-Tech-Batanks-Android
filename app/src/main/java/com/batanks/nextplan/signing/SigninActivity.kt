package com.batanks.nextplan.signing

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.core.text.buildSpannedString
import androidx.core.text.color
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
import com.batanks.nextplan.home.isValidPassword
import com.batanks.nextplan.signing.viewmodel.SigninViewModel
import com.batanks.nextplan.splash.SplashActivity
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.Login
import com.batanks.nextplan.swagger.model.User
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_signin.*
import java.util.regex.Pattern

class SigninActivity : BaseAppCompatActivity(), View.OnClickListener {

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

        loginTextField.markRequiredInRed()
        passTextField.markRequiredInRed()

        dismissKeyboard()

        getSharedPreferences(RetrofitClient.USER_TOKEN_PREF, Context.MODE_PRIVATE).edit().clear().apply()

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

                    val response_User = response.data as User

                    val id: Int? = response_User?.id
                    val userName: String? = response_User?.username
                    val firstName: String? = response_User?.first_name
                    val lastName: String? = response_User?.last_name
                    val email: String? = response_User?.email
                    val phoneNumber: String? = response_User?.phone_number
                    val image : String? = response_User?.picture

                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putInt("ID", id!!).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("USERNAME", userName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("FIRSTNAME", firstName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("LASTNAME", lastName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("EMAIL", email).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("PHONENUMBER", phoneNumber).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("PROFILEIMAGE", image).apply()

                    getSharedPreferences(RetrofitClient.USER_TOKEN_PREF, Context.MODE_PRIVATE).edit().putString("USER_LOGIN_TOKEN",response_User.token).apply()

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

      //loadingDialog = this.getLoadingDialog(0, R.string.signing_in_please_wait, theme = R.style.AlertDialogCustom)

        createAccount.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
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

    fun TextInputLayout.markRequiredInRed() {

        hint = buildSpannedString {
            append(hint)
            color(Color.RED) { append(" *") }
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
        when (v?.id) {
            R.id.login -> {

                if (isValidUsername(loginTextField.editText?.text.toString()) && loginTextField.editText?.length()!! >= 2){

                    if (isValidPassword(passTextField.editText?.text?.toString()) && passTextField.editText?.length()!! >= 6){

                        dismissKeyboard()
                        //RetrofitClient.cookieJar?.clear()
                        getSharedPreferences(SplashActivity.PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(SplashActivity.PREF_NAME, stayLoggedInCheckBox.isChecked).apply()
                        val login = Login(login = loginTextField?.editText?.text.toString(), password = passTextField?.editText?.text.toString())
                        signinViewModel.performSignIn(login)

                    } else {

                        passTextField.editText?.setError(getString(R.string.invalid_password))
                        passTextField.editText?.requestFocus()
                    }


                } else {

                    loginTextField.editText?.setError(getString(R.string.invalid_username))
                    loginTextField.editText?.requestFocus()
                }
/*
                if (!TextUtils.isEmpty(loginTextField?.editText?.text.toString()) && !TextUtils.isEmpty(passTextField?.editText?.text.toString())){

                    dismissKeyboard()
                    //RetrofitClient.cookieJar?.clear()
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
                }*/
            }
        }
    }

    override fun onBackPressed() {

        showDialog()
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.exit_pop_up)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /*val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)*/

        val btn_create_followups_cancel = dialog.findViewById(R.id.btn_create_followups_cancel) as Button
        val btn_create_followups_ok = dialog.findViewById(R.id.btn_create_followups_ok) as Button

        btn_create_followups_cancel.setOnClickListener { dialog.dismiss() }

        btn_create_followups_ok.setOnClickListener {

            dialog.dismiss()

            finishAffinity()
        }

        dialog.show()

        dialog.window?.decorView?.setOnTouchListener { v, event ->

            if (event?.action == MotionEvent.ACTION_DOWN) {

                val imm = v?.getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                v.clearFocus()
            }
            false
        }
    }

    //private fun isValidPassword(textToCheck: String?): Boolean = textPattern.matcher(textToCheck).matches()
    private fun isValidUsername(textToCheck: String?) : Boolean = userNamePattern.matcher(textToCheck).matches()

    companion object {
        val textPattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#?!@$%^&()\\[\\\\\\]<>*~:_/|`-])(?=\\S+$)(?=.*\\d).+$")
        val userNamePattern: Pattern = Pattern.compile("^(?=\\S+\$).+$")
    }
}