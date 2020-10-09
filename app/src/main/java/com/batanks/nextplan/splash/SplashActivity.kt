package com.batanks.nextplan.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.signing.SigninActivity
import com.batanks.nextplan.signing.viewmodel.SplashViewModel
import com.batanks.nextplan.swagger.api.AuthenticationAPI

class SplashActivity : BaseAppCompatActivity() {

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

        loadingDialog = this.getLoadingDialog(0, R.string.loading_please_wait, theme = R.style.AlertDialogCustom)

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
                    getSharedPreferences(RetrofitClient.USER_TOKEN_PREF, Context.MODE_PRIVATE).edit().clear().apply()
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
}