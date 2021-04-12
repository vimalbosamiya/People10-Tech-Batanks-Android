package com.batanks.nextplan.arch

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.viewmodel.SettingsViewModel
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.SettingsGet
import com.batanks.nextplan.utils.ContextUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*


open class BaseAppCompatActivity : AppCompatActivity(), BaseContract.BasicLoadingView {

    var loadingDialog: AlertDialog? = null
    var settingsResponse : SettingsGet? = null
    var language : String? = null
    var lang : String? = null

    private val settingsViewModel: SettingsViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                SettingsViewModel(it)
            }
        }).get(SettingsViewModel::class.java)
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

    override fun handleError(error: Throwable) {}

    override fun showMessage(message: String) {
        hideLoader()
        MaterialAlertDialogBuilder(this)
                .setTitle(message)
                .setMessage(null)
                .setNegativeButton(getString(android.R.string.no)) { _, _ -> }
                .setPositiveButton(getString(android.R.string.yes)) { _, _ -> }
                .setCancelable(false)
                .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideLoader()
    }

    override fun showMessage(message: String, title: String, showPositiveButton: Boolean) {}
    override fun context(): Context = this

  /*  override fun attachBaseContext(newBase: Context) {
// get chosen language from shread preference
        val localeToSwitchTo = PreferenceManager(newBase).getAppLanguage()
        val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(newBase, localeToSwitchTo)
        super.attachBaseContext(localeUpdatedContext)
    }*/

    override fun attachBaseContext(newBase: Context?) {

        language   = newBase?.getSharedPreferences("SAVED_LANG", MODE_PRIVATE)?.getString("SAVED_LANGUAGE","en")
        //println("Saved Language from base is : " + language)

       /* settingsViewModel.getSettings()

        settingsViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    settingsResponse = response.data as SettingsGet

                    language = settingsResponse!!.language

                    println(language)

                }

                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        if (language == null){

            lang = "en"

        } else {

            lang = language
        }*/

        val localeToSwitchTo = Locale(language)
        println(language)
        val localeUpdatedContext = ContextUtils.updateLocale(newBase!!, localeToSwitchTo)
        super.attachBaseContext(localeUpdatedContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*settingsViewModel.getSettings()

        settingsViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    settingsResponse = response.data as SettingsGet

                    language = settingsResponse!!.language

                    println(language)

                }

                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })*/
    }

    override fun onPostResume() {
        super.onPostResume()

       /* settingsViewModel.getSettings()

        settingsViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    settingsResponse = response.data as SettingsGet

                    language = settingsResponse!!.language

                }

                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })*/
    }
}