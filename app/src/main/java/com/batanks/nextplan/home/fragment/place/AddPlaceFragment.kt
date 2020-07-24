package com.batanks.nextplan.home.fragment.place

import android.content.Context
import android.graphics.Rect
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.swagger.model.EventPlace
import com.batanks.nextplan.swagger.model.Place
import kotlinx.android.synthetic.main.activity_followups.*
import kotlinx.android.synthetic.main.fragment_add_place.*

class AddPlaceFragment(val listener: AddPlaceFragmentListener) : BaseDialogFragment(), View.OnClickListener {

    lateinit var fragview: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragview = inflater.inflate(R.layout.fragment_add_place, container, false)

        return fragview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadingDialog = requireContext().getLoadingDialog(0, R.string.fetching_location, theme = R.style.AlertDialogCustom)

        fragview.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if  (event?.action == MotionEvent.ACTION_DOWN) {
                    if(v is EditText) {
                        val outRect = Rect()
                        v.getGlobalVisibleRect(outRect)
                        if (!outRect.contains(event.rawX as Int, event.rawY as Int)) {
                            v.clearFocus()
                            val imm = v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                        }
                    }
                }
                return false
            }
        })

        ok.setOnClickListener(this)
        cancel.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ok -> {

                if (!TextUtils.isEmpty(planNameTextField?.editText?.text.toString())){

                    showLoader()
                    dismissKeyboard()
                    val place = Place(name = planNameTextField?.editText?.text.toString(),
                            address = addressTextField?.editText?.text.toString(),
                            zipcode = zipCodeTextField?.editText?.text.toString(),
                            city = townTextField?.editText?.text.toString(),
                            country = ""/*ccp_activity_country.getSelectedCountryName()*//*countryTextField?.editText?.text.toString()*/,
                            map = enableMapSupportCheckBox.isChecked,
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
                        //showMessage("We are unable to find the location info, Please enter a different location.")
                        Toast.makeText(activity,"We are unable to find the location info, Please enter a different location.",Toast.LENGTH_LONG).show()
                    } else {
                        place.latitude = result[0].latitude
                        place.longitude = result[0].longitude
                        listener.addPlaceFragmentAddressFetch(place)

                        //Toast.makeText(activity,place.toString(),Toast.LENGTH_LONG).show()
                    }
                    hideLoader()
                }else {

                    if(TextUtils.isEmpty(planNameTextField?.editText?.text.toString())){

                        //actionNameTextField.error = "Action name is Required"
                        planNameTextField.editText?.error = "Place name is Required"
                        //Toast.makeText(activity,"Action name cannot be empty",Toast.LENGTH_SHORT).show()
                    }
                }

            }

            R.id.cancel -> {

                listener.cancelPlaceFragmentAddressFetch()

            }
        }
    }

    interface AddPlaceFragmentListener {
        fun addPlaceFragmentAddressFetch(place: Place)
        fun cancelPlaceFragmentAddressFetch()
    }
}