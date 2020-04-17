package com.batanks.newplan.home.fragment.place

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.batanks.newplan.R
import kotlinx.android.synthetic.main.fragment_add_place.*
import java.lang.StringBuilder

class AddPlaceFragment : DialogFragment(), View.OnClickListener {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ok.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ok -> {
                val stringBuilder = StringBuilder()
                        .append(planNameTextField?.editText?.text)
                        .append("+")
                        .append(addressTextField?.editText?.text)
                        .append("+")
                        .append(townTextField?.editText?.text)
                        .append("+")
                        .append(countryTextField?.editText?.text)
                        .append("+")
                        .append(zipCodeTextField?.editText?.text)

                val result: List<Address> = Geocoder(v.context).getFromLocationName(stringBuilder.toString(), 5)
                println(result.size)
            }
        }
    }
}