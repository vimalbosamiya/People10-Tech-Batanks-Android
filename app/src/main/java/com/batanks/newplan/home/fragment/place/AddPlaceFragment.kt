package com.batanks.newplan.home.fragment.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.batanks.newplan.R

class AddPlaceFragment : DialogFragment() {

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = requireActivity().layoutInflater.inflate(R.layout.fragment_add_place, null)
        return MaterialAlertDialogBuilder(requireActivity(), R.style.AppTheme_Dialog)
                .setTitle("Add Place")
                .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ -> })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ -> })
                .setCancelable(false)
                .setView(view)
                .create()
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_place, container, false)
    }
}