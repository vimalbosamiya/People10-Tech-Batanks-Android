package com.batanks.nextplan.Settings

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Adapters.FollowupsAdapter_Settings
import com.batanks.nextplan.Settings.viewmodel.FiltersViewModel
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.dismissKeyboard
import com.batanks.nextplan.home.markRequiredInRed
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.FilterAPI
import com.batanks.nextplan.swagger.model.AddFilter
import com.batanks.nextplan.swagger.model.FilterResultsList
import com.batanks.nextplan.swagger.model.Filters
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_followups.*

class Followups : BaseAppCompatActivity(), FollowupsAdapter_Settings.FollowupsAdapter_SettingsCallBack {

    lateinit var rv_settings_followups : RecyclerView
    lateinit var adapter : FollowupsAdapter_Settings
    var followUpList : ArrayList<String> = arrayListOf()
    var filtersList : ArrayList<FilterResultsList> = arrayListOf()


    private val filtersViewModel:FiltersViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(FilterAPI::class.java)?.let {
                FiltersViewModel(it)
            }
        }).get(FiltersViewModel::class.java)
    }

    var filter_obj : Filters? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followups)

        filtersViewModel.getFiltersList()
        loadData()

        followup_extFab.setOnClickListener(View.OnClickListener {
            createFollowUpDialog()
        })

        img_followups_close.setOnClickListener {

            finish()
        }

        filtersViewModel.updateresponseLiveData.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    Toast.makeText(this,getString(R.string.filter_updated), Toast.LENGTH_SHORT).show()
                }
                Status.FAILURE -> {
                    hideLoader()
                    showMessage(response.data.toString())
                }
            }
        })

        /*rv_settings_followups = findViewById(R.id.rv_settings_followups)
        rv_settings_followups.layoutManager = LinearLayoutManager(this)*/
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

                val str: String = tip_create_followups_name.editText?.text.toString()

                var present : Boolean = false

                for (item in followUpList){

                    if (!followUpList.contains(str)){

                        present = false

                    } else {

                        //present = true
                        //Toast.makeText(this,getString(R.string.follow_up_exist),Toast.LENGTH_SHORT).show()
                    }
                }

                if (present == false){

                    followUpList?.add(str)

                    saveData(false)

                    loadData()

                    dialog.dismiss()
                    dismissKeyboard()
                }

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

    private fun editFollowUpDialog(position: Int, name: String?, keyword: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_edit_followups)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

      /*  val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)*/




        val btn_edit_followups_cancel = dialog.findViewById(R.id.btn_edit_followups_cancel) as Button
        val btn_edit_followups_ok = dialog.findViewById(R.id.btn_edit_followups_ok) as Button
        val tip_edit_followups_name = dialog.findViewById(R.id.tip_edit_followups_name) as TextInputLayout
        val input_edit_followups_name = dialog.findViewById(R.id.input_edit_followups_name) as TextInputEditText

        input_edit_followups_name.setText(name)

        btn_edit_followups_cancel.setOnClickListener {

            dismissKeyboard()
            dialog.dismiss()
        }
        println(keyword)
        btn_edit_followups_ok.setOnClickListener {

            if (tip_edit_followups_name.editText?.length()!! >= 1){

                filtersViewModel.updateFilter(
                        AddFilter(input_edit_followups_name.text.toString(),keyword),position)

                    Toast.makeText(this,getString(R.string.filter_updated),Toast.LENGTH_SHORT).show()

                    dialog.dismiss()

                    dismissKeyboard()

                   filtersViewModel.getFiltersList()

//                val str: String = tip_edit_followups_name.editText?.text.toString()
//
//                followUpList.set(position,str)
//
//                saveData(true)
//
              // loadData()

                //dialog.dismiss()

            } else {

                tip_edit_followups_name.editText?.setError(getString(R.string.follow_up_name_empty))
                input_edit_followups_name.requestFocus()
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

    private fun saveData(fromEdit :Boolean){

        val sharedPreference =  getSharedPreferences("FOLLOW _UP_PREFERENCE",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        val gson = Gson()

        val json = gson.toJson(followUpList)
        editor.putString("follow up list", json)
        editor.apply()

        if (fromEdit == true){ Toast.makeText(this,getString(R.string.filter_updated),Toast.LENGTH_SHORT).show()
        }else if (fromEdit == false){Toast.makeText(this,getString(R.string.filter_created),Toast.LENGTH_SHORT).show()}

    }

    private fun loadData() {
        filtersViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()
                    filter_obj = response.data as Filters?
                    filtersList = filter_obj?.results!!
                    println(filtersList.size)
                    buildRecyclerView()
                }

                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
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
//
//        buildRecyclerView()
    }

    private fun buildRecyclerView() {
        rv_settings_followups = findViewById(R.id.rv_settings_followups)
        rv_settings_followups.layoutManager = LinearLayoutManager(this)
        rv_settings_followups.setHasFixedSize(true)
        adapter = filtersList?.let { FollowupsAdapter_Settings(this,it) }!!
        rv_settings_followups.adapter = adapter
        //rv_settings_followups?.getAdapter()?.getItemCount()?.let { rv_settings_followups.smoothScrollToPosition(it) }
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

    override fun editButtonFollowUpItemListener(pos: Int, name: String?, keyword: String?) {

        editFollowUpDialog(pos,name,keyword)
    }

    override fun closeButtonFollowUpItemListener(pos: Int) {
        rv_settings_followups?.adapter?.notifyDataSetChanged()
    }

    override fun deleteButtonFollowUpItemListener(pos: Int?) {
        filtersViewModel.deleteFilter(pos)
        filtersViewModel.deleteresponseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()
                    Toast.makeText(this,response.data.toString(),Toast.LENGTH_SHORT).show()
                    //showMessage(response.data.toString())
                    filtersViewModel.getFiltersList()
                }

                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
                else -> {}
            }
        })
    }
}
