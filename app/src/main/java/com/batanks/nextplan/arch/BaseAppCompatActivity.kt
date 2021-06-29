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
}