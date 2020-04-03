package com.batanks.newplan.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.batanks.newplan.R
import com.batanks.newplan.common.dialogBuilder
import com.batanks.newplan.common.dismissKeyboard
import com.batanks.newplan.common.getLoadingDialog
import com.batanks.newplan.home.HomePlanPreview
import com.batanks.newplan.registration.contract.RegistrationContract
import com.batanks.newplan.registration.presenter.RegistrationPresenter
import com.batanks.newplan.signing.SigninActivity
import com.batanks.newplan.swagger.model.RegisterUser
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.regex.Pattern

class Registration : AppCompatActivity(), RegistrationContract.IView, View.OnTouchListener, View.OnClickListener {

    private var presenter: RegistrationPresenter? = null
    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.registration)

        presenter = RegistrationPresenter()
        presenter?.onAttach(this)

        loadingDialog = this.getLoadingDialog(0, R.string.creating_user_please_wait, theme = R.style.AlertDialogCustom)

        signIn.setOnClickListener(this)

        passwordEyeIcon.setOnTouchListener(this)
        confirmPasswordEyeIcon.setOnTouchListener(this)

        userName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                userNameValidation()
            }
        })

        firstName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                firstNameValidation()
            }
        })

        lastName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                lastNameValidation()
            }
        })

        email.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (emailValidation()) {
                    email.error = null
                } else {
                    email.error = "Enter valid email id."
                }
            }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                passwordEditTextValidation()
            }
        })

        confirmPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                confirmPasswordEditTextValidation()
            }
        })

        phoneNumber.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    private fun userNameValidation(): Boolean {
        if (userName.text.toString().isEmpty()) {
            userName.error = "UserName should contain at least 1 letter."
        } else {
            userName.error = null
            return true
        }
        return false
    }

    private fun firstNameValidation(): Boolean {
        if (firstName.text.toString().isEmpty()) {
            firstName.error = "FirstName should contain at least 1 letter."
        } else {
            firstName.error = null
            return true
        }
        return false
    }

    private fun lastNameValidation(): Boolean {
        if (lastName.text.toString().isEmpty()) {
            lastName.error = "LastName should contain at least 1 letter."
        } else {
            lastName.error = null
            return true
        }
        return false
    }

    private fun emailValidation(): Boolean {
        if (isEmailValid(email.text)) {
            return true
        }
        return false
    }

    private fun passwordEditTextValidation(): Boolean {
        if (passwordEditText.text.toString().isEmpty() || !isValidPassword(passwordEditText.text.toString())) {
            passwordEditText.error = "Password should contain at least 1 lower case and 1 upper case letter and 1 digit."
        } else {
            passwordEditText.error = null
            return true
        }
        return false
    }

    private fun confirmPasswordEditTextValidation(): Boolean {
        if (confirmPasswordEditText.text.toString().isEmpty() || !isValidPassword(confirmPasswordEditText.text.toString())) {
            confirmPasswordEditText.error = "Password should contain at least 1 lower case and 1 upper case letter and 1 digit."
        } else {
            confirmPasswordEditText.error = null
            return true
        }
        return false
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

    override fun showMessage(message: String, title: String, showPositiveButton: Boolean) {

    }

    override fun context(): Context {
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        hideLoader()
        presenter?.onDetach()
        presenter = null
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
                /*if (passwordEditTextValidation() || confirmPasswordEditTextValidation()) {
                    this.dialogBuilder(
                            title = getString(R.string.password_condition),
                            positive = getString(android.R.string.yes),
                            negative = getString(android.R.string.no),
                            cancelable = false,
                            theme = R.style.AlertDialogCustom_Register
                    ).create().show()
                } else*/ if (passwordEditText.text.toString() != confirmPasswordEditText.text.toString()) {
                    this.dialogBuilder(
                            title = getString(R.string.password_doesnt_match),
                            positive = getString(android.R.string.yes),
                            negative = getString(android.R.string.no),
                            cancelable = false,
                            theme = R.style.AlertDialogCustom_Register
                    ).create().show()
                } else if (!isEmailValid(email.text)) {
                    this.dialogBuilder(
                            title = getString(R.string.not_valid_email),
                            positive = getString(android.R.string.yes),
                            negative = getString(android.R.string.no),
                            cancelable = false,
                            theme = R.style.AlertDialogCustom_Register
                    ).create().show()
                } else {
                    showLoader()
                    val user = RegisterUser(
                            username = userName.text.toString(),
                            first_name = firstName.text.toString(),
                            last_name = lastName.text.toString(),
                            email = email.text.toString(),
                            password1 = passwordEditText.text.toString(),
                            password2 = confirmPasswordEditText.text.toString(),
                            phone_number = phoneNumber.text.toString()
                    )
                    presenter?.createUser(user)
                }
            }
        }
    }

    private fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(textToCheck: String?): Boolean {
        return textPattern.matcher(textToCheck).matches()
    }

    override fun processResponse() {
        Toast.makeText(this, "User account is created", Toast.LENGTH_SHORT).show()
        hideLoader()
        val intent = Intent(this, HomePlanPreview::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        val textPattern: Pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")
    }
}