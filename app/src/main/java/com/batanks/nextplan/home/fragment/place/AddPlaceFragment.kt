package com.batanks.nextplan.home.fragment.place

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.swagger.model.Place
import kotlinx.android.synthetic.main.fragment_add_place.*

class AddPlaceFragment(val listener: AddPlaceFragmentListener) : BaseDialogFragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_place, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadingDialog = requireContext().getLoadingDialog(0, R.string.fetching_location, theme = R.style.AlertDialogCustom)
        ok.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ok -> {
                showLoader()
                dismissKeyboard()
                val place = Place(name = planNameTextField?.editText?.text.toString(),
                        address = addressTextField?.editText?.text.toString(),
                        zipcode = zipCodeTextField?.editText?.text.toString(),
                        city = townTextField?.editText?.text.toString(),
                        country = countryTextField?.editText?.text.toString(),
                        map = false,
                        latitude = 0.0,
                        longitude = 0.0)

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

                val result: List<Address> = Geocoder(view.context).getFromLocationName(stringBuilder.toString(), 5)
                if (result.isEmpty()) {
                    showMessage("We are unable to find the location info, Please enter a different location.")
                } else {
                    place.latitude = result[0].latitude
                    place.longitude = result[0].longitude
                    listener.addPlaceFragmentAddressFetch(place)
                }
                hideLoader()
            }
        }
    }

    interface AddPlaceFragmentListener {
        fun addPlaceFragmentAddressFetch(place: Place)
    }
}