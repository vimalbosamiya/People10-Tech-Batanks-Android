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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
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
import com.batanks.nextplan.home.adapter.HomePlanPreviewAdapter
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
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import kotlinx.android.synthetic.main.fragment_search_public_event_issue.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.selects.whileSelect
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchAllEventFragment (val filter : String?) : BaseFragment(), CoroutineScope, CategoryAdapter.CategoryRecyclerViewCallBack {

    lateinit var eventsRecyclerView : RecyclerView

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

    var eventList : ArrayList<GetEventListHome> = arrayListOf()
    var dummyList : ArrayList<GetEventListHome> = arrayListOf()
    lateinit var categoryList : ArrayList<CategoryList>

    private var searchKeyword : String? = null
    private var searchCategory : CategoryList? = null
    private var searchCategoryId : String? = "-1"

    private var textChangedJob: Job? = null
    private lateinit var textListener: TextWatcher
    private lateinit var job: Job

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_search_public_event_issue, container, false)

        //addcontactSearchEditText.setText(filter)

        if (!filter.isNullOrEmpty()){ SearchViewModel.searchText = filter }

        eventsRecyclerView = view.findViewById(R.id.publicEventRecyclerView)
        eventsRecyclerView?.setHasFixedSize(true)
        eventsRecyclerView.layoutManager = LinearLayoutManager(activity)

        job = Job()

        textListener = object : TextWatcher {
            private var searchFor = "A" // Or view.editText.text.toString()

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText != searchFor) {
                    searchFor = searchText

                    textChangedJob?.cancel()
                    textChangedJob = launch(Dispatchers.Main) {
                        //delay(2000L)
                        if (searchText == searchFor) {

                            searchKeyword = searchText

                            if (!searchText.isNullOrEmpty()){

                                SearchViewModel.searchText = searchText

                            }else if(searchText.isNullOrEmpty()){

                                SearchViewModel.searchText = null
                            }

                            if (searchCategory != null){

                                searchCategoryId = searchCategory!!.pk.toString()

                                searchViewModel.getSearchList("ALL",searchCategoryId.toString(),searchKeyword)

                            } else if (searchCategory == null){

                                searchViewModel.apiSearchListWithTypeAndKeyword("ALL",searchKeyword)
                            }

                           // view.hideKeyboard()

                            println(searchText )

                            SearchViewModel.searchText = searchText
                            //loadList(searchText)
                        }
                    }
                }
            }
        }

        return view
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* if(!filter.isNullOrEmpty()){
            
            addcontactSearchEditText.setText(filter)
            searchViewModel.apiSearchListWithTypeAndKeyword(getString(R.string.all),filter)
            //searchViewModel.getSearchList(getString(R.string.all),searchCategoryId.toString(),searchKeyword)

        }else if (filter.isNullOrEmpty()){

            //searchViewModel.getSearchList(getString(R.string.all),searchCategoryId.toString(),searchKeyword)
            searchViewModel.apiSearchListWithType("ALL")
        }*/

       /* if (!searchViewModel.searchText.isNullOrEmpty()){

            addcontactSearchEditText.setText(searchViewModel.searchText)
            searchViewModel.apiSearchListWithTypeAndKeyword(getString(R.string.all),searchViewModel.searchText)

        }else if (searchViewModel.searchText.isNullOrEmpty()){

            //searchViewModel.getSearchList(getString(R.string.all),searchCategoryId.toString(),searchKeyword)
            searchViewModel.apiSearchListWithType("ALL")
        }*/

        apiSearch()

        //loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)
        //searchViewModel.apiSearchListWithType("ALL")

        searchViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    searchViewModel.response = response.data as InlineResponse2002

                    eventList = searchViewModel.response!!.results

                    if (eventList.size <= 1) {
                        val params = eventsRecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        eventsRecyclerView.setLayoutParams(params)
                    }

                    eventsRecyclerView?.adapter = HomePlanPreviewAdapter(false, eventList/*,this*/)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        searchViewModel.responseLiveDataWithType.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    searchViewModel.response = response.data as InlineResponse2002

                    eventList = searchViewModel.response!!.results

                    if (eventList.size <= 1) {
                        val params = eventsRecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        eventsRecyclerView.setLayoutParams(params)
                    }

                    eventsRecyclerView?.adapter = HomePlanPreviewAdapter(false, eventList/*,this*/)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        searchViewModel.responseLiveDataWithTypeAndCategory.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    searchViewModel.response = response.data as InlineResponse2002

                    eventList = searchViewModel.response!!.results

                    if (eventList.size <= 1) {
                        val params = eventsRecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        eventsRecyclerView.setLayoutParams(params)
                    }

                    eventsRecyclerView?.adapter = HomePlanPreviewAdapter(false, eventList/*,this*/)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        searchViewModel.responseLiveDataWithTypeAndKeyword.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    searchViewModel.response = response.data as InlineResponse2002

                    eventList = searchViewModel.response!!.results

                    if (eventList.size <= 1) {
                        val params = eventsRecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        eventsRecyclerView.setLayoutParams(params)
                    }

                    eventsRecyclerView?.adapter = HomePlanPreviewAdapter(false, eventList/*,this*/)
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

                    categoryList = categoryViewModel.response!!.results

//                    categoryRecyclerView?.adapter = CategoryAdapter(categoryList,view.context)

                    println(categoryViewModel.categoryList)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        sort.setOnClickListener {

            showSortDialog(view!!.context)
        }
    }

    private fun showSortDialog(context : Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.category_pop_up)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btn_cancel = dialog.findViewById(R.id.btn_cancel) as Button
        val btn_ok = dialog.findViewById(R.id.btn_ok) as Button
        val categoryRecyclerView = dialog.findViewById(R.id.categoryRecyclerView) as RecyclerView
        categoryRecyclerView?.layoutManager = LinearLayoutManager(activity)

        categoryRecyclerView?.adapter = view?.context?.let { CategoryAdapter(categoryList, it,this, searchCategoryId?.toInt()) }

        btn_cancel.setOnClickListener {

            dialog.dismiss()
        }

        btn_ok.setOnClickListener {

            println(searchCategory?.name)

            dialog.dismiss()

            if (searchCategory != null ){

                searchCategoryId = searchCategory!!.pk.toString()

            }

            if (searchCategory != null && searchKeyword != null){

               // searchCategoryId = searchCategory!!.pk.toString()

                searchViewModel.getSearchList("ALL",searchCategoryId.toString(),searchKeyword)

            } else if (searchCategory == null && searchKeyword != null){

                searchViewModel.apiSearchListWithTypeAndKeyword("ALL",searchKeyword)

            }else if (searchCategory == null && searchKeyword == null){

                searchViewModel.apiSearchListWithType("ALL")

            }else if(searchCategory != null && searchKeyword == null){

                //searchCategoryId = searchCategory!!.pk.toString()

                searchViewModel.apiSearchListWithTypeAndCategory("ALL", searchCategoryId.toString())
            }

            //searchViewModel.getSearchList(getString(R.string.all),searchCategory?.pk.toString(),searchKeyword)
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

    private fun apiSearch(){

        if (!SearchViewModel.searchText.isNullOrEmpty()){

            addcontactSearchEditText.setText(SearchViewModel.searchText)
            searchViewModel.apiSearchListWithTypeAndKeyword("ALL",SearchViewModel.searchText)

        }else if (SearchViewModel.searchText.isNullOrEmpty()){

            //searchViewModel.getSearchList(getString(R.string.all),searchCategoryId.toString(),searchKeyword)
            searchViewModel.apiSearchListWithType("ALL")
        }
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser && isResumed()){

            onResume()

        } else{

            println("All visible text set to null")
        }
    }

    override fun onResume() {
        super.onResume()
        addcontactSearchEditText.addTextChangedListener(textListener)

        if (!getUserVisibleHint()) { return }

       /* if(!filter.isNullOrEmpty()){

            addcontactSearchEditText.setText(filter)
            searchViewModel.apiSearchListWithTypeAndKeyword(getString(R.string.all),filter)
            //searchViewModel.getSearchList(getString(R.string.all),searchCategoryId.toString(),searchKeyword)

        } else if (filter.isNullOrEmpty()){

            //searchViewModel.getSearchList(getString(R.string.all),searchCategoryId.toString(),searchKeyword)
            searchViewModel.apiSearchListWithType("ALL")
        }*/

      /*  if (!searchViewModel.searchText.isNullOrEmpty()){

            addcontactSearchEditText.setText(searchViewModel.searchText)
            searchViewModel.apiSearchListWithTypeAndKeyword(getString(R.string.all),searchViewModel.searchText)

        }else if (searchViewModel.searchText.isNullOrEmpty()){

            //searchViewModel.getSearchList(getString(R.string.all),searchCategoryId.toString(),searchKeyword)
            searchViewModel.apiSearchListWithType("ALL")
        }*/

        apiSearch()

        //searchViewModel.apiSearchListWithType("ALL")

        //eventsRecyclerView?.adapter = HomePlanPreviewAdapter(eventList/*,this*/)

            //addcontactSearchEditText.setText("")

        // eventsRecyclerView?.adapter = HomePlanPreviewAdapter(eventList/*,this*/)
        //(eventsRecyclerView?.adapter as HomePlanPreviewAdapter).notifyDataSetChanged()

        /*val fragment : SearchAllEventFragment = SearchAllEventFragment(null)

        fragmentManager?.beginTransaction()?.detach(fragment)?.attach(fragment)?.commit()*/

    }

    override fun onPause() {
        addcontactSearchEditText.removeTextChangedListener(textListener)
        super.onPause()
    }

    override fun onDestroy() {
        textChangedJob?.cancel()
        super.onDestroy()
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun selectedCategory(selectedCategory: CategoryList?) {

        searchCategory = selectedCategory

        //println(searchCategory)
    }
}