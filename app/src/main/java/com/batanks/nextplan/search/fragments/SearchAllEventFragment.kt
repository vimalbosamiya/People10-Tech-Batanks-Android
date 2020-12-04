package com.batanks.nextplan.search.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
/*import com.batanks.nextplan.home.adapter.HomePlanPreviewAdapter*/
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel.CategoryViewModel
import com.batanks.nextplan.home.viewmodel.HomePlanPreviewViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.search.adapters.CategoryAdapter
import com.batanks.nextplan.search.viewmodel.SearchViewModel
import com.batanks.nextplan.swagger.api.CategoryAPI
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.api.SearchAPI
import com.batanks.nextplan.swagger.model.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_search_public_event_issue.*

class SearchAllEventFragment : BaseFragment()  {

    lateinit var eventsRecyclerView : RecyclerView
    //lateinit var categoryRecyclerView : RecyclerView
    var categoryRecyclerView : RecyclerView? = null
    //lateinit var eventsAdapter : HomePlanPreviewAdapter

    private val homePlanPreviewViewModel: HomePlanPreviewViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(EventAPI::class.java)?.let {
                    HomePlanPreviewViewModel(it)
                }
            }
        }).get(HomePlanPreviewViewModel::class.java)
    }

    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(SearchAPI::class.java)?.let {
                    SearchViewModel(it)
                }
            }
        }).get(SearchViewModel::class.java)
    }

    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(CategoryAPI::class.java)?.let {
                    CategoryViewModel(it)
                }
            }
        }).get(CategoryViewModel::class.java)
    }

    lateinit var eventList : ArrayList<GetEventListHome>
    lateinit var searchList : ArrayList<GetEventListHome>
    lateinit var categoryList : List<CategoryList>
    //var categoryList : List<CategoryList>? = null

    var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_search_public_event_issue, container, false)

        eventsRecyclerView = view.findViewById(R.id.publicEventRecyclerView)
        eventsRecyclerView.layoutManager = LinearLayoutManager(activity)

        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        categoryRecyclerView?.layoutManager = LinearLayoutManager(activity)

        //eventsRecyclerView.adapter = HomePlanPreviewAdapter(listOf<String>())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        /*categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        categoryRecyclerView?.layoutManager = LinearLayoutManager(activity)*/
        //categoryRecyclerView.layoutManager = LinearLayoutManager(activity)

        loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        homePlanPreviewViewModel.eventList()

        homePlanPreviewViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    homePlanPreviewViewModel.response = response.data as InlineResponse2002

                    eventList = homePlanPreviewViewModel.response!!.results

                    //eventsRecyclerView?.adapter = HomePlanPreviewAdapter(eventList)

                    //eventAdapter.notifyDataSetChanged()
                    //var events_list = listOf(response.data as EventList)
                    //var res : EventListResponse = response.data as EventListResponse
                    //println(response.data )
                    //println(eventList)
                    //var events_list = res.results
                    //eventList = res.results
                    //if(events_list != null)
                    //recyclerView?.adapter = HomePlanPreviewAdapter(listOf<String>())
                    //recyclerView?.adapter = HomePlanPreviewAdapter(events_list)
                    //println(events_list)

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        categoryViewModel.getCategoryList()

        categoryViewModel.responseLiveData.observe(viewLifecycleOwner, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    categoryViewModel.response = response.data as InlineResponse200

                    categoryViewModel.categoryList = categoryViewModel.response!!.results

                    categoryList = response.data.results

                    //categoryRecyclerView?.adapter = CategoryAdapter(categoryList)

                    println(categoryViewModel.categoryList)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        floating_action_button.setOnClickListener {

            showDialog(view!!.context)
        }

        sort.setOnClickListener {

            showSortDialog(view!!.context)
        }

        addcontactSearchTextField.addOnEditTextAttachedListener {

            //println("Typing")
        }

        addcontactSearchEditText.doOnTextChanged { text, start, count, after ->

            /*Handler().postDelayed({

                println(addcontactSearchEditText.text)

            },3 * 1000)*/
        }

        addcontactSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                Handler().postDelayed({

                    println(addcontactSearchEditText.text )

                    searchViewModel.getSearchList("PRIVATE",null, addcontactSearchEditText.text.toString())

                    searchViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->

                        when (response.status) {
                            Status.LOADING -> {
                                showLoader()
                            }
                            Status.SUCCESS -> {
                                hideLoader()

                                searchViewModel.response = response.data as InlineResponse2002

                                searchList = searchViewModel.response!!.results

                                //eventsRecyclerView?.adapter = HomePlanPreviewAdapter(searchList)

                                /*eventAdapter.notifyDataSetChanged()
                                var events_list = listOf(response.data as EventList)
                                var res : EventListResponse = response.data as EventListResponse
                                println(response.data )
                                println(eventList)
                                var events_list = res.results
                                eventList = res.results
                                if(events_list != null)
                                recyclerView?.adapter = HomePlanPreviewAdapter(listOf<String>())
                                recyclerView?.adapter = HomePlanPreviewAdapter(events_list)
                                println(events_list)*/

                            }
                            Status.ERROR -> {
                                hideLoader()
                                showMessage(response.error?.message.toString())
                            }
                        }
                    })

                },3 * 1000)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })

        fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
            this.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(editable: Editable?) {
                    afterTextChanged.invoke(editable.toString())
                }
            })
        }

        /*addcontactSearchEditText.afterTextChanged {

            Handler().postDelayed({

                println(addcontactSearchEditText.text )

                *//*searchViewModel.getSearchList("PRIVATE",null, addcontactSearchEditText.text.toString())

                searchViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->

                    when (response.status) {
                        Status.LOADING -> {
                            showLoader()
                        }
                        Status.SUCCESS -> {
                            hideLoader()

                            searchViewModel.response = response.data as InlineResponse2002

                            searchList = searchViewModel.response!!.results

                            eventsRecyclerView?.adapter = HomePlanPreviewAdapter(searchList)

                            //eventAdapter.notifyDataSetChanged()
                            //var events_list = listOf(response.data as EventList)
                            //var res : EventListResponse = response.data as EventListResponse
                            //println(response.data )
                            //println(eventList)
                            //var events_list = res.results
                            //eventList = res.results
                            //if(events_list != null)
                            //recyclerView?.adapter = HomePlanPreviewAdapter(listOf<String>())
                            //recyclerView?.adapter = HomePlanPreviewAdapter(events_list)
                            //println(events_list)

                        }
                        Status.ERROR -> {
                            hideLoader()
                            showMessage(response.error?.message.toString())
                        }
                    }
                })*//*

            },3 * 1000)
        }*/


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

                tip_create_followups_name.editText?.error = "Follow Up name should contain atleast one character"
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

    private fun showSortDialog(context : Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.category_pop_up)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //categoryRecyclerView?.adapter = CategoryAdapter(categoryViewModel.categoryList!!)
        categoryRecyclerView?.adapter = CategoryAdapter(categoryList)

        /*val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)*/

        val btn_cancel = dialog.findViewById(R.id.btn_cancel) as Button
        val btn_ok = dialog.findViewById(R.id.btn_ok) as Button

        /*dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);*/

        /*tip_create_followups_name.editText?.setOnFocusChangeListener(object:View.OnFocusChangeListener {

            override fun onFocusChange(v:View, hasFocus:Boolean) {
                if (hasFocus)
                {
                    dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                }
            }
        })

        input_create_followups_name.setOnFocusChangeListener(object:View.OnFocusChangeListener {

            override fun onFocusChange(v:View, hasFocus:Boolean) {
                if (hasFocus)
                {
                    dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                }
            }
        })*/


        btn_cancel.setOnClickListener {

            dialog.dismiss()
        }

        btn_ok.setOnClickListener {


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