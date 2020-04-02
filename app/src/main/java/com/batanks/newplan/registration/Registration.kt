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
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.batanks.newplan.R
import com.batanks.newplan.common.dialogBuilder
import com.batanks.newplan.common.getLoadingDialog
import com.batanks.newplan.registration.contract.RegistrationContract
import com.batanks.newplan.registration.presenter.RegistrationPresenter
import com.batanks.newplan.signing.SigninActivity
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

        loadingDialog = getLoadingDialog(0, R.string.signing_in_please_wait)
        loadingDialog?.setCancelable(false)

        signIn.setOnClickListener(this)

        passwordEyeIcon.setOnTouchListener(this)
        confirmPasswordEyeIcon.setOnTouchListener(this)

        name.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {

            }
        })

        firstName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {

            }
        })

        email.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {

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
    }

    private fun passwordEditTextValidation(): Boolean {
        if (passwordEditText.text.toString().isEmpty()) {
            passwordEditText.error = "Password should contain at least 1 lower case and 1 upper case letter."
        } else {
            passwordEditText.error = null
            return true
        }
        return false
    }

    private fun confirmPasswordEditTextValidation(): Boolean {
        if (confirmPasswordEditText.text.toString().isEmpty()) {
            confirmPasswordEditText.error = "Password should contain at least 1 lower case and 1 upper case letter."
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

    }

    override fun showMessage(message: String) {

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
        when (v?.id) {
            R.id.signIn -> {
                if (isTextValid(passwordEditText.text.toString()) && isTextValid(confirmPasswordEditText.text.toString())) {
                    this.dialogBuilder(
                            title = R.string.password_condition,
                            positive = android.R.string.yes,
                            negative = android.R.string.no,
                            cancelable = false
                    ).create().show()
                } else if (passwordEditText == confirmPasswordEditText) {
                    this.dialogBuilder(
                            title = R.string.password_doesnt_match,
                            positive = android.R.string.yes,
                            negative = android.R.string.no,
                            cancelable = false
                    ).create().show()
                } else if (isEmailValid(email.text)) {
                    this.dialogBuilder(
                            title = R.string.not_valid_email,
                            positive = android.R.string.yes,
                            negative = android.R.string.no,
                            cancelable = false
                    ).create().show()
                } else {

                }
            }
        }
    }

    private fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isTextValid(textToCheck: String?): Boolean {
        return textPattern.matcher(textToCheck).matches()
    }

    companion object {
        val textPattern: Pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")
    }
}