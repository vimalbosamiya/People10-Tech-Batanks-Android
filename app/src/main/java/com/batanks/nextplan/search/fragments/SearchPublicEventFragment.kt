package com.batanks.nextplan.search.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel.CategoryViewModel
/*import com.batanks.nextplan.home.adapter.HomePlanPreviewAdapter*/
import com.batanks.nextplan.home.viewmodel.HomePlanPreviewViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.search.adapters.CategoryAdapter
import com.batanks.nextplan.search.viewmodel.SearchViewModel
import com.batanks.nextplan.swagger.api.CategoryAPI
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.api.SearchAPI
import com.batanks.nextplan.swagger.model.*
import kotlinx.android.synthetic.main.fragment_search_public_event_issue.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchPublicEventFragment : BaseFragment(), CoroutineScope, CategoryAdapter.CategoryRecyclerViewCallBack {

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

    lateinit var eventList : ArrayList<GetEventListHome>
    lateinit var categoryList : ArrayList<CategoryList>

    private var searchKeyword : String? = null
    private var searchCategory : CategoryList? = null
    private var searchCategoryId : String? = "-1"

    private var textChangedJob: Job? = null
    private lateinit var textListener: TextWatcher
    private lateinit var job: Job

    var publicEventList :  ArrayList<GetEventListHome> = arrayListOf()
    lateinit var searchList : ArrayList<GetEventListHome>
    var categoryRecyclerView : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        if (!SearchViewModel.searchText.isNullOrEmpty()){


        }

        val view = inflater.inflate(R.layout.fragment_search_public_event_issue, container, false)

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

                                searchViewModel.getSearchList("PUBLIC",searchCategoryId.toString(),searchKeyword)

                            } else if (searchCategory == null){

                                searchViewModel.apiSearchListWithTypeAndKeyword("PUBLIC",searchKeyword)
                            }

                            //view.hideKeyboard()

                            println(searchText )
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

        apiSearch()

        //loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)
        //searchViewModel.getSearchList(getString(R.string._public),null,null)

        //searchViewModel.apiSearchListWithType("PUBLIC")

        searchViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    searchViewModel.response = response.data as InlineResponse2002

                    publicEventList = searchViewModel.response!!.results

                    eventsRecyclerView?.adapter = HomePlanPreviewAdapter(false, publicEventList /*,this*/)
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

                    publicEventList = searchViewModel.response!!.results

                    eventsRecyclerView?.adapter = HomePlanPreviewAdapter(false, publicEventList /*,this*/)
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

                    publicEventList = searchViewModel.response!!.results

                    eventsRecyclerView?.adapter = HomePlanPreviewAdapter(false, publicEventList /*,this*/)
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

    private fun apiSearch(){

        if (!SearchViewModel.searchText.isNullOrEmpty()){

            addcontactSearchEditText.setText(SearchViewModel.searchText)
            searchViewModel.apiSearchListWithTypeAndKeyword("PUBLIC",SearchViewModel.searchText)

        }else if (SearchViewModel.searchText.isNullOrEmpty()){

            //searchViewModel.getSearchList(getString(R.string.all),searchCategoryId.toString(),searchKeyword)
            searchViewModel.apiSearchListWithType("PUBLIC")
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

            dialog.dismiss()

            if (searchCategory != null ){

                searchCategoryId = searchCategory!!.pk.toString()
            }

            if (searchCategory != null && searchKeyword != null){

                searchViewModel.getSearchList("PUBLIC",searchCategoryId.toString(),searchKeyword)

            } else if (searchCategory == null && searchKeyword != null){

                searchViewModel.apiSearchListWithTypeAndKeyword("PUBLIC",searchKeyword)

            }else if (searchCategory == null && searchKeyword == null){

                searchViewModel.apiSearchListWithType("PUBLIC")

            }else if(searchCategory != null && searchKeyword == null){

                searchViewModel.apiSearchListWithTypeAndCategory("PUBLIC", searchCategoryId.toString())
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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser && isResumed()){

            onResume()

            println("public visible")
        }
    }

    override fun onResume() {
        super.onResume()
        addcontactSearchEditText.addTextChangedListener(textListener)

        if (!getUserVisibleHint()) { return }

        apiSearch()

       /* searchViewModel.apiSearchListWithType("PUBLIC")
        addcontactSearchEditText.setText("")*/

        //eventsRecyclerView?.adapter = HomePlanPreviewAdapter(publicEventList /*,this*/)


        println("public visible working fine")
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