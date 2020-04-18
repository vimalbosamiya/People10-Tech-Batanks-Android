package com.batanks.newplan.arch

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

open class BaseFragment : Fragment(), BaseContract.BasicLoadingView {

    var loadingDialog: AlertDialog? = null

    override fun showLoader() {
        val progress = loadingDialog
        if (progress != null && !progress.isShowing) {
            progress.show()
        }
    }

    override fun hideLoader() {
        val progress = loadingDialog
        if (!this.isAdded && progress != null && progress.isShowing) {
            progress.dismiss()
        }
    }

    override fun handleError(error: Throwable) {}

    override fun showMessage(message: String) {
        hideLoader()
        MaterialAlertDialogBuilder(requireContext())
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
    override fun context(): Context = requireContext()
}