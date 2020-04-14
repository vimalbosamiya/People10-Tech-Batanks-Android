package com.batanks.newplan.home.fragment.tabfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.batanks.newplan.R
import com.batanks.newplan.home.fragment.AddPeriodFragment
import com.batanks.newplan.home.fragment.AddPlaceFragment
import com.batanks.newplan.home.fragment.spinner.CustomArrayAdapter
import com.batanks.newplan.home.fragment.spinner.SpinnerModel
import kotlinx.android.synthetic.main.fragment_public_new_plan.*
import kotlinx.android.synthetic.main.layout_add_plan_add_place.*

class PublicPlanFragment : Fragment(), ButtonContract, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_public_new_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateCategory()

        /*addPeriodButton.setOnClickListener(this)*/
        addPlaceButton.setOnClickListener(this)
    }

    private fun populateCategory() {
        val customSpinner = CustomArrayAdapter(requireContext(), listOf(
                SpinnerModel("Trip", R.drawable.ic_category_trip),
                SpinnerModel("Professional", R.drawable.ic_category_professional),
                SpinnerModel("Leisure", R.drawable.ic_category_leisure),
                SpinnerModel("Institutional", R.drawable.ic_category_institutional),
                SpinnerModel("Other", R.drawable.ic_category_others)))
        (categoryTextField.editText as? AutoCompleteTextView)?.setAdapter(customSpinner)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addPeriodButton -> {
                addPeriodClicked()
            }
            R.id.addPlaceButton -> {
                addPlaceClicked()
            }
        }
    }

    override fun addPeriodClicked() {
        fragmentManager?.let { AddPeriodFragment().show(it, AddPeriodFragment::class.java.simpleName) }
    }

    override fun addPlaceClicked() {
        requireActivity().supportFragmentManager
                .beginTransaction()
                .add(AddPlaceFragment(), AddPlaceFragment::class.java.canonicalName)
                .commitAllowingStateLoss()
    }

    override fun addActionClicked() {

    }

    override fun addActivityClicked() {

    }

    override fun addPeopleClicked() {

    }
}