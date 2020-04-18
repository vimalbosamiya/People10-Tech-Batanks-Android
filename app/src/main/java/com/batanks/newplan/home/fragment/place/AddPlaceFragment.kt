package com.batanks.newplan.home.fragment.place

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.batanks.newplan.R
import com.batanks.newplan.arch.BaseDialogFragment
import com.batanks.newplan.common.getLoadingDialog
import com.batanks.newplan.swagger.model.Place
import kotlinx.android.synthetic.main.fragment_add_place.*
import java.lang.StringBuilder

class AddPlaceFragment : BaseDialogFragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_place, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadingDialog = requireContext().getLoadingDialog(0, R.string.creating_user_please_wait, theme = R.style.AlertDialogCustom)
        ok.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ok -> {
                showLoader()
                val place = Place(name = planNameTextField?.editText?.text.toString(),
                        address = addressTextField?.editText?.text.toString(),
                        zipcode = zipCodeTextField?.editText?.text.toString(),
                        city = townTextField?.editText?.text.toString(),
                        country = countryTextField?.editText?.text.toString(),
                        map = false,
                        latitude = "33",
                        longitude = "43")

                val stringBuilder = StringBuilder()
                        .append(place.name)
                        .append("+")
                        .append(place.address)
                        .append("+")
                        .append(place.city)
                        .append("+")
                        .append(place.country)
                        .append("+")
                        .append(place.zipcode)

                val result: List<Address> = Geocoder(v.context).getFromLocationName(stringBuilder.toString(), 5)
                (result.size)
                println(result.size)
                hideLoader()
            }
        }
    }
}

interface AddPlaceFragmentClickEvents {
    fun addPlaceOkayButtonClicked()
    fun addPlaceCancelButtonClicked()
}