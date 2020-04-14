package com.batanks.newplan.home.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.batanks.newplan.R

class AddPeriodFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view: View = requireActivity().layoutInflater.inflate(R.layout.layout_add_plan_add_period, null)

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which -> })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> })
        builder.setView(view)

        return builder.create()
    }
}