package com.batanks.newplan.signing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.batanks.newplan.R
import com.batanks.newplan.common.dialogBuilder
import com.batanks.newplan.common.dismissKeyboard
import com.batanks.newplan.common.getLoadingDialog
import com.batanks.newplan.home.HomePlanPreview
import com.batanks.newplan.registration.Registration
import com.batanks.newplan.signing.contract.SigninContract
import com.batanks.newplan.signing.presenter.SigninPresenter
import com.batanks.newplan.swagger.model.Login
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity(), SigninContract.IView, View.OnTouchListener, View.OnClickListener {

    private var presenter: SigninPresenter? = null
    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        presenter = SigninPresenter()
        presenter?.onAttach(this)

        loadingDialog = this.getLoadingDialog(0, R.string.signing_in_please_wait, theme = R.style.AlertDialogCustom)

        createAccount.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
            finish()
        }

        passwordEyeIcon.setOnTouchListener(this)

        loginEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                loginEditTextValidation()
            }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                passwordEditTextValidation()
            }
        })

        login.setOnClickListener(this)
    }

    fun loginEditTextValidation(): Boolean {
        if (loginEditText.text.toString().isEmpty()) {
            loginEditText.error = "Password should contain at least 1 letter."
        } else {
            loginEditText.error = null
            return true
        }
        return false
    }

    fun passwordEditTextValidation(): Boolean {
        if (passwordEditText.text.toString().isEmpty()) {
            passwordEditText.error = "Password should contain at least 1 letter."
        } else {
            passwordEditText.error = null
            return true
        }
        return false
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
        }
        return false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                dismissKeyboard()
                if (loginEditTextValidation() && passwordEditTextValidation()) {
                    showLoader()
                    val login = Login(login = loginEditText.text.toString(), password = passwordEditText.text.toString())
                    presenter?.performLogin(login)
                }
            }
        }
    }

    override fun processResponse() {
        hideLoader()
        val intent = Intent(this, HomePlanPreview::class.java)
        startActivity(intent)
        finish()
    }
}