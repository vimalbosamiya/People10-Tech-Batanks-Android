package com.batanks.nextplan.search

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Followups
import com.batanks.nextplan.Settings.viewmodel.FiltersViewModel
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.dismissKeyboard
import com.batanks.nextplan.eventdetails.viewmodel.AddContactViewModel
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.home.markRequiredInRed
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.search.viewmodel.SearchViewModel
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.api.FilterAPI
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.api.SearchAPI
import com.batanks.nextplan.swagger.model.AddContact
import com.batanks.nextplan.swagger.model.AddFilter
import com.batanks.nextplan.swagger.model.FilterResultsList
import com.batanks.nextplan.swagger.model.Filters
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.searchToolBar
import kotlinx.android.synthetic.main.fragment_search.tabs
import kotlinx.android.synthetic.main.fragment_search.view_pager
import java.lang.reflect.Type

class Search : BaseAppCompatActivity() {

    var followUpList : ArrayList<String> = arrayListOf()
    //val followups = Followups()
    var filtersList : ArrayList<FilterResultsList> = arrayListOf()
    var filter_obj : Filters? = null


    private val filterViewModel: FiltersViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(FilterAPI::class.java)?.let {
                FiltersViewModel(it)
            }
        }).get(FiltersViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val filter : String? = intent.getStringExtra("FILTER")

        loadData()

        val toolbar: Toolbar = findViewById(R.id.searchToolBar)
        setSupportActionBar(toolbar)

        //this.supportActionBar?.title = "Search"
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_header)

        searchToolBar.setNavigationOnClickListener {

            intent = Intent(this, HomePlanPreview :: class.java)
            startActivity(intent)
            SearchViewModel.searchText = null
            finish()
        }

        floating_action_button.setOnClickListener {

            //showDialog(this)
            //followups.createFollowUpDialog()
            createFollowUpDialog()
        }

        filterViewModel.postresponseLiveData.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    Toast.makeText(this,getString(R.string.filter_created), Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })


        val tabsPagerAdapter = SearchTabsAdapter(supportFragmentManager, filter)
        view_pager.adapter = tabsPagerAdapter

        tabs.setupWithViewPager(view_pager)

        val tabOne = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabOne.text = getString(R.string.all)
        tabOne.setTextColor(resources.getColor(R.color.colorWhite))
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_people_tablayout, 0, 0)
        tabs.getTabAt(0)?.customView = tabOne

        val tabTwo = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabTwo.text = getString(R.string._public)
        tabTwo.setTextColor(resources.getColor(R.color.colorWhite))
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_public_plan_tablayout, 0, 0)
        tabs.getTabAt(1)?.customView = tabTwo

        val tabThree = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabThree.text = getString(R.string._private)
        tabThree.setTextColor(resources.getColor(R.color.colorWhite))
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_private_plan_tablayout, 0, 0)
        tabs.getTabAt(2)?.customView = tabThree

        tabs.getTabAt(0)?.icon = getDrawable(R.drawable.ic_people_tablayout)
        tabs.getTabAt(1)?.icon = getDrawable(R.drawable.ic_public_plan_tablayout)
        tabs.getTabAt(2)?.icon = getDrawable(R.drawable.ic_private_plan_tablayout)
    }

    private fun createFollowUpDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_create_followups)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /*val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)*/

        val btn_create_followups_cancel = dialog.findViewById(R.id.btn_create_followups_cancel) as Button
        val btn_create_followups_ok = dialog.findViewById(R.id.btn_create_followups_ok) as Button
        val tip_create_followups_name = dialog.findViewById(R.id.tip_create_followups_name) as TextInputLayout
        val input_create_followups_name = dialog.findViewById(R.id.input_create_followups_name) as TextInputEditText

        tip_create_followups_name.markRequiredInRed()


        btn_create_followups_cancel.setOnClickListener { dialog.dismiss() }



        btn_create_followups_ok.setOnClickListener {



            if (tip_create_followups_name.editText?.length()!! >= 1){

                filterViewModel.addFilter(
                    AddFilter(input_create_followups_name.text.toString(),SearchViewModel.searchText))
                Toast.makeText(this,getString(R.string.filter_created),Toast.LENGTH_SHORT).show()

                    loadData()

                    dialog.dismiss()
                    dismissKeyboard()

//                val str: String = tip_create_followups_name.editText?.text.toString()
//
//                var present : Boolean = false
//
//                for (item in followUpList){
//
//                    if (!followUpList.contains(str)){
//
//                        present = false
//
//                    } else {
//
//                        //present = true
//                        //Toast.makeText(this,getString(R.string.follow_up_exist),Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                if (present == false){
//
//                    followUpList?.add(str)
//                    saveData()
//                    loadData()
//
//                    dialog.dismiss()
//                    dismissKeyboard()
//                }

            } else {

                tip_create_followups_name.editText?.setError(getString(R.string.follow_up_name_empty))

                input_create_followups_name.requestFocus()

            }
        }

        dialog.show()

        dialog.window?.decorView?.setOnTouchListener { v, event ->

            if (event?.action == MotionEvent.ACTION_DOWN) {

                val imm = v?.getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                v.clearFocus()
            }
            false
        }
    }

    private fun saveData(){

        val sharedPreference =  getSharedPreferences("FOLLOW _UP_PREFERENCE",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        val gson = Gson()

        val json = gson.toJson(followUpList)
        editor.putString("follow up list", json)
        editor.apply()
        Toast.makeText(this,getString(R.string.filter_created),Toast.LENGTH_SHORT).show()
    }

    private fun loadData() {

        filterViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()
                    filter_obj = response.data as Filters?
                    filtersList = filter_obj?.results!!
                    println(filtersList.size)
                }

                Status.FAILURE -> {
                    hideLoader()
                    showMessage(response.data.toString())
                }
                else -> {}
            }
        })

//        val sharedPreferences = getSharedPreferences("FOLLOW _UP_PREFERENCE", Context.MODE_PRIVATE)
//        val gson = Gson()
//        val json = sharedPreferences.getString("follow up list", null)
//        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
//
//        if (json != null){
//
//            followUpList = gson?.fromJson<ArrayList<String>>(json, type)
//
//        } else{
//
//            //println()
//        }
//
//        if (followUpList.size == 0) { followUpList = ArrayList() }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = getCurrentFocus()
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm?.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun showDialog(context : Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_create_followups)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /*val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)*/

        val btn_create_followups_cancel = dialog.findViewById(R.id.btn_create_followups_cancel) as Button
        val btn_create_followups_ok = dialog.findViewById(R.id.btn_create_followups_ok) as Button
        val tip_create_followups_name = dialog.findViewById(R.id.tip_create_followups_name) as TextInputLayout
        val input_create_followups_name = dialog.findViewById(R.id.input_create_followups_name) as TextInputEditText

        tip_create_followups_name.markRequiredInRed()

        /*dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        tip_create_followups_name.editText?.setOnFocusChangeListener(object:View.OnFocusChangeListener {

            override fun onFocusChange(v:View, hasFocus:Boolean) {
                if (hasFocus)
                {
                    dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                }
            }
        })*/

        /*input_create_followups_name.setOnFocusChangeListener(object:View.OnFocusChangeListener {

            override fun onFocusChange(v:View, hasFocus:Boolean) {
                if (hasFocus)
                {
                    dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                }
            }
        })*/


        btn_create_followups_cancel.setOnClickListener {

            dialog.dismiss()
        }

        btn_create_followups_ok.setOnClickListener {

            //            if (TextUtils.isEmpty(tip_create_followups_name.editText?.text.toString())){
            if (tip_create_followups_name.editText?.length()!! >= 1){

                dialog.dismiss()

            } else {

                tip_create_followups_name.editText?.setError(getString(R.string.follow_up_name_empty))
                input_create_followups_name.requestFocus()
            }
        }

        //input_create_followups_name.requestFocus()

        dialog.show()

        /*dialog.window?.decorView?.setOnFocusChangeListener(object:View.OnFocusChangeListener {

            override fun onFocusChange(v:View, hasFocus:Boolean) {
                if (hasFocus)
                {
                    dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                }
            }
        })*/

        dialog.window?.decorView?.setOnTouchListener { v, event ->

            if (event?.action == MotionEvent.ACTION_DOWN) {

                val imm = v?.getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                v.clearFocus()
            }
            false
        }

        /*view.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if (event?.action == MotionEvent.ACTION_DOWN) {

                    val imm = v?.getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                    v.clearFocus()
                }

                return false
            }
        })*/

    }
}
