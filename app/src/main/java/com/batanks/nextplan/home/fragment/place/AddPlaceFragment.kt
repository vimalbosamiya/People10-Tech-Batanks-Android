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
import com.batanks.nextplan.swagger.model.PostPlaceInfo
import com.batanks.nextplan.swagger.model.PostPlaces
import com.google.android.material.textfield.TextInputEditText
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


        view.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if (event?.action == MotionEvent.ACTION_DOWN) {

                            val imm = v?.getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                            v.clearFocus()
                        }

                return false
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ok -> {

                var zipcode : Int = 0

                if (placeNameTextField.editText?.length()!! >= 2){

                    showLoader()
                    dismissKeyboard()

                    if (!TextUtils.isEmpty(zipCodeTextField?.editText?.text.toString())){

                        zipcode = zipCodeTextField?.editText?.text.toString().toInt()

                        println(zipcode)
                    }

                    val place = PostPlaceInfo(name = placeNameTextField?.editText?.text.toString(),
                            address = addressTextField?.editText?.text.toString(),
                            zipcode = zipcode,
                            city = townTextField?.editText?.text.toString(),
                            country = ccp_activity_country.getSelectedCountryName()/*countryTextField?.editText?.text.toString()*/,
                            map = enableMapSupportCheckBox.isChecked )

                    val eventPlace = PostPlaces(place,
                            name = placeNameTextField?.editText?.text.toString(),
                            address = addressTextField?.editText?.text.toString(),
                            zipcode = zipcode,
                            city = townTextField?.editText?.text.toString(),
                            country = ccp_activity_country.getSelectedCountryName(),
                            map = enableMapSupportCheckBox.isChecked,
                            visibility = enableMapSupportCheckBox.isChecked
                    )

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

                    if (enableMapSupportCheckBox.isChecked){

                        if (result.isEmpty()) {
                            //showMessage("We are unable to find the location info, Please enter a different location.")
                            Toast.makeText(activity,getString(R.string.place_not_found),Toast.LENGTH_LONG).show()

                        }else {
                            //place.latitude = result[0].latitude
                            //place.longitude = result[0].longitude
                            listener.addPlaceFragmentAddressFetch(eventPlace)

                            //Toast.makeText(activity,place.toString(),Toast.LENGTH_LONG).show()
                        }
                    } else {

                        listener.addPlaceFragmentAddressFetch(eventPlace)
                    }

                    hideLoader()
                }else {

                        //actionNameTextField.error = "Action name is Required"
                        placeNameTextField.editText?.setError(getString(R.string.place_name_error))
                        placeNameTextField.requestFocus()
                        //Toast.makeText(activity,"Action name cannot be empty",Toast.LENGTH_SHORT).show()
                }
            }

            R.id.cancel -> {

                listener.cancelPlaceFragmentAddressFetch()
            }
        }
    }

    interface AddPlaceFragmentListener {
        fun addPlaceFragmentAddressFetch(place: PostPlaces)
        fun cancelPlaceFragmentAddressFetch()
    }
}