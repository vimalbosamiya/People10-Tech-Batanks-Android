package com.batanks.newplan.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.newplan.R
import com.batanks.newplan.arch.BaseContract
import com.batanks.newplan.arch.response.Status
import com.batanks.newplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.newplan.common.dialogBuilder
import com.batanks.newplan.common.getLoadingDialog
import com.batanks.newplan.home.HomePlanPreview
import com.batanks.newplan.network.RetrofitClient
import com.batanks.newplan.signing.SigninActivity
import com.batanks.newplan.signing.viewmodel.SplashViewModel
import com.batanks.newplan.swagger.api.AuthenticationAPI

class SplashActivity : AppCompatActivity(), BaseContract.BasicLoadingView {

    private var loadingDialog: AlertDialog? = null

    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                SplashViewModel(it)
            }
        }).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            if (getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(PREF_NAME, false)) {
                splashViewModel.getUserProfile()
            } else {
                startActivity(Intent(this, SigninActivity::class.java))
                finish()
            }
        }, SPLASH_SCREEN_TIMEOUT)

        loadingDialog = this.getLoadingDialog(0, R.string.signing_in_please_wait, theme = R.style.AlertDialogCustom)

        splashViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()
                    startActivity(Intent(this, HomePlanPreview::class.java))
                    finish()
                }
                Status.ERROR -> {
                    hideLoader()
                    startActivity(Intent(this, SigninActivity::class.java))
                    finish()
                }
            }
        })
    }

    companion object {
        const val SPLASH_SCREEN_TIMEOUT: Long = 3 * 1000 //times in milliseconds
        const val PREF_NAME = "stay_loggedin"
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
                theme = R.style.AlertDialogCustom
        ).create().show()
    }

    override fun showMessage(message: String, title: String, showPositiveButton: Boolean) {}

    override fun context(): Context {
        return this
    }
}