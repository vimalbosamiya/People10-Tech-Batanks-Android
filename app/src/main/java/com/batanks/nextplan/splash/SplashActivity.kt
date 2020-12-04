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
import com.batanks.nextplan.home.ModelPreferencesManager
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.signing.SigninActivity
import com.batanks.nextplan.signing.viewmodel.SplashViewModel
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.User

class SplashActivity : BaseAppCompatActivity() {

    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                SplashViewModel(it)
            }
        }).get(SplashViewModel::class.java)
    }

    var user_obj : User? = null

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
                    //showLoader()
                }
                Status.SUCCESS -> {
                    //hideLoader()

                    startActivity(Intent(this, HomePlanPreview::class.java))
                    finish()

                    user_obj = response.data as User

                    val id: Int? = user_obj?.id
                    val userName: String? = user_obj?.username
                    val firstName: String? = user_obj?.first_name
                    val lastName: String? = user_obj?.last_name
                    val email: String? = user_obj?.email
                    val phoneNumber: String? = user_obj?.phone_number

                    ModelPreferencesManager.put(user_obj, "USER_DATA")

                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putInt("ID", id!!).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("USERNAME", userName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("FIRSTNAME", firstName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("LASTNAME", lastName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("EMAIL", email).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("PHONENUMBER", phoneNumber).apply()
                }
                Status.ERROR -> {
                    //hideLoader()
                    getSharedPreferences(RetrofitClient.USER_TOKEN_PREF, Context.MODE_PRIVATE).edit().clear().apply()
                    startActivity(Intent(this, SigninActivity::class.java))
                    finish()
                }
            }
        })
    }

    companion object {
        const val SPLASH_SCREEN_TIMEOUT: Long = 2 * 1000 //times in milliseconds
        const val PREF_NAME = "stay_loggedin"
    }
}