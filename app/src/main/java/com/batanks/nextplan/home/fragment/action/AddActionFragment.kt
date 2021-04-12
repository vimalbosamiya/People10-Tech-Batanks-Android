package com.batanks.nextplan.home.fragment.action

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.home.markRequiredInRed
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_add_action.*
import kotlinx.android.synthetic.main.fragment_add_action_card.*

class AddActionFragment (val listner : AddActionFragmentListener, private val position : Int, private val taskList : ArrayList<PostTasks>, private val event : Event?,
                         private val editButtonClicked : Boolean): BaseDialogFragment() , View.OnClickListener,
                         AssignPeopleFragment.AssignPeopleFragmentListner{

    private val newTask : Int = -1

    private var assignee : Int? = null

    private val eventDetailViewModel: EventDetailViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(EventAPI::class.java)?.let {
                    EventDetailViewModel(it)
                }
            }
        }).get(EventDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        actionNameTextField.markRequiredInRed()

        if(position >= 0){

            var natureOfCost : String? = null

            //txt_add_action_assignee_id.setText(taskList[position].assignee!!)
            actionNameEditText.setText(taskList[position].name)
            actionDescriptionEditText.setText(taskList[position].description)
            costActionEditText.setText(taskList[position].price.toString())

            if (taskList[position].per_person == true){ natureOfCost = "Cost Per Person" } else { natureOfCost = "Total Cost" }

            actv_action_nature_of_the_cost.setText(natureOfCost)

            if (event!!.tasks[position].assignee != null){

                rl_add_action_assignee_holder.visibility = View.VISIBLE

                txt_add_action_assignee_name.setText(event!!.tasks[position].assignee?.username)

                if (event!!.tasks[position].assignee?.picture != null){

                    activity?.let { Glide.with(it).load(event!!.tasks[position].assignee?.picture).circleCrop().into(img_add_action_assignee) }
                }
            }
        }

        loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

      /*  if (!actionNameTextField.isEndIconVisible){

            actionNameEditText.setPadding(16,16,16,16)
        }*/

        assignParticipantButton.setOnClickListener(this)
        ok.setOnClickListener(this)
        add_action_cancel.setOnClickListener(this)

        costActionEditText.addTextChangedListener(object: TextWatcher {

            override fun afterTextChanged(s: Editable) {
                //costActionEditText.setText(costActionEditText.getText().toString() + "€")
            }
            override fun beforeTextChanged(s:CharSequence, start:Int, count:Int, after:Int) {}
            override fun onTextChanged(s:CharSequence, start:Int, before:Int, count:Int) {}
        })

        val nature_of_the_cost = arrayOf("Cost Per Person" , "Total Cost")

        val adapter = activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, nature_of_the_cost) }
        actv_action_nature_of_the_cost.setAdapter(adapter)
        actv_action_nature_of_the_cost.threshold = 1

        // Set an item click listener for auto complete text view

        actv_action_nature_of_the_cost.onItemClickListener = AdapterView.OnItemClickListener{
            parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()

            // Display the clicked item using toast
            //Toast.makeText(activity,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }


        // Set a dismiss listener for auto complete text view
        actv_action_nature_of_the_cost.setOnDismissListener {
            //Toast.makeText(activity,"Suggestion closed.",Toast.LENGTH_SHORT).show()

            //natureOfTheCostTextField.hint = null
        }
        // Set a focus change listener for auto complete text view
        actv_action_nature_of_the_cost.onFocusChangeListener = View.OnFocusChangeListener{
            view, b ->
            if(b){
                // Display the suggestion dropdown on focus
                actv_action_nature_of_the_cost.showDropDown()
                dismissKeyboard()
            }
        }

        /*view.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if (event?.action == MotionEvent.ACTION_DOWN) {
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
        })*/

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

            R.id.assignParticipantButton -> {

                if (editButtonClicked == true){

                    requireActivity().supportFragmentManager
                       .beginTransaction()
                       .add(AssignPeopleFragment(this, event, true, null), AssignPeopleFragment::class.java.canonicalName)
                       .commitAllowingStateLoss()

                }else {

                    Toast.makeText(activity,getString(R.string.add_assignee_toast),Toast.LENGTH_LONG).show()
                }
            }

            R.id.ok -> {

                if (actionNameTextField?.editText?.length()!! >= 2) {

                    var perPerson : Boolean = false


                    if (natureOfTheCostTextField.editText?.text.toString() == "Cost Per Person"){

                        perPerson = true

                    } else if (natureOfTheCostTextField.editText?.text.toString() == "Total Cost"){

                        perPerson = false
                    }

                   /* if (!TextUtils.isEmpty(txt_add_action_assignee_id.text.toString())){

                        assignee = txt_add_action_assignee_id.text.toString().toInt()
                    }*/

                    var price : Int = 0

                    if (!TextUtils.isEmpty(costActionTextField?.editText?.text.toString())){

                        price = costActionTextField?.editText?.text.toString().toInt()
                    }

                    val task = PostTasks(price =  price,
                            name = actionNameTextField?.editText?.text.toString(),
                            description = actionDescriptionTextField?.editText?.text.toString(),
                            per_person = perPerson,
                            assignee = /*txt_add_action_assignee_name.text.toString())*/ /*txt_add_action_assignee_id.text.toString().toInt()*/ assignee
                            /*assigneeName = txt_add_action_assignee_name.text.toString()*/)

                    if (editButtonClicked == true ){

                        if (position >= 0){

                            if (assignee!= null){

                                eventDetailViewModel.apiEventFullTaskPatch(event?.id.toString(), event!!.tasks[position].id.toString(), TaskPost(price.toString(),
                                        actionNameTextField?.editText?.text.toString(),actionDescriptionTextField?.editText?.text.toString(),
                                        perPerson, assignee!!))

                                eventDetailViewModel.responseLiveDataTaskPatchFull.observe(viewLifecycleOwner, Observer { response ->

                                    when (response.status) {
                                        Status.LOADING -> {
                                            showLoader()
                                        }

                                        Status.SUCCESS -> {
                                            hideLoader()

                                            //eventDetailViewModel.getEventData(eventId.toString())

                                            listner.AddActionFragmentFetch(position,task)

                                            //println("patch action is working fine without assignee")
                                        }
                                        Status.ERROR -> {
                                            hideLoader()
                                            showMessage(response.error?.message.toString())
                                            println(response.error?.message.toString())
                                        }
                                    }
                                })

                            } else if (assignee == null) {

                                eventDetailViewModel.apiEventTaskPatchWithoutAssignee(event?.id.toString(), event!!.tasks[position].id.toString(),
                                        TaskPostWithoutAssignee(price.toString(), actionNameTextField?.editText?.text.toString(),actionDescriptionTextField?.editText?.text.toString(),
                                                perPerson))

                                eventDetailViewModel.responseLiveDataTaskPatchWithoutAssignee.observe(viewLifecycleOwner, Observer { response ->

                                    when (response.status) {
                                        Status.LOADING -> {
                                            showLoader()
                                        }

                                        Status.SUCCESS -> {
                                            hideLoader()

                                            //eventDetailViewModel.getEventData(eventId.toString())

                                            listner.AddActionFragmentFetch(position,task)

                                            //println("patch action is working fine without assignee")
                                        }
                                        Status.ERROR -> {
                                            hideLoader()

                                            showMessage(response.error?.message.toString())
                                            println(response.error?.message.toString())
                                        }
                                    }
                                })
                            }
                        } else {

                            eventDetailViewModel.apiEventTaskCreate(event?.id.toString(), TaskPost(price.toString(), actionNameTextField?.editText?.text.toString(),
                                    actionDescriptionTextField?.editText?.text.toString(), perPerson, assignee))

                            eventDetailViewModel.responseLiveDataCreateTask.observe(viewLifecycleOwner, Observer { response ->

                                when (response.status) {
                                    Status.LOADING -> {
                                        showLoader()
                                    }

                                    Status.SUCCESS -> {
                                        hideLoader()

                                        //eventDetailViewModel.getEventData(eventId.toString())

                                        listner.AddActionFragmentFetch(position,task)

                                        //println("patch action is working fine without assignee")
                                    }
                                    Status.ERROR -> {
                                        hideLoader()

                                        showMessage(response.error?.message.toString())
                                        println(response.error?.message.toString())
                                    }
                                }
                            })


                            /*listner.AddActionFragmentFetch(newTask,task)
                            actionNameTextField.error = null*/
                        }

                    }else {

                        listner.AddActionFragmentFetch(newTask,task)
                    }

                    /*if (position >= 0){

                        if (assignee!= null){

                            eventDetailViewModel.apiEventFullTaskPatch(event?.id.toString(), event!!.tasks[position].id.toString(), TaskPost(price.toString(),
                                    actionNameTextField?.editText?.text.toString(),actionDescriptionTextField?.editText?.text.toString(),
                                    perPerson, assignee!!))

                            eventDetailViewModel.responseLiveDataTaskPatchFull.observe(viewLifecycleOwner, Observer { response ->

                                when (response.status) {
                                    Status.LOADING -> {
                                        showLoader()
                                    }

                                    Status.SUCCESS -> {
                                        hideLoader()

                                        //eventDetailViewModel.getEventData(eventId.toString())

                                        listner.AddActionFragmentFetch(position,task)

                                        //println("patch action is working fine without assignee")
                                    }
                                    Status.ERROR -> {
                                        hideLoader()
                                        showMessage(response.error?.message.toString())
                                        println(response.error?.message.toString())
                                    }
                                }
                            })

                        } else if (assignee == null) {

                            eventDetailViewModel.apiEventTaskPatchWithoutAssignee(event?.id.toString(), event!!.tasks[position].id.toString(),
                                    TaskPostWithoutAssignee(price.toString(), actionNameTextField?.editText?.text.toString(),actionDescriptionTextField?.editText?.text.toString(),
                                    perPerson))

                            eventDetailViewModel.responseLiveDataTaskPatchWithoutAssignee.observe(viewLifecycleOwner, Observer { response ->

                                when (response.status) {
                                    Status.LOADING -> {
                                        showLoader()
                                    }

                                    Status.SUCCESS -> {
                                        hideLoader()

                                        //eventDetailViewModel.getEventData(eventId.toString())

                                        listner.AddActionFragmentFetch(position,task)

                                        //println("patch action is working fine without assignee")
                                    }
                                    Status.ERROR -> {
                                        hideLoader()

                                        showMessage(response.error?.message.toString())
                                        println(response.error?.message.toString())
                                    }
                                }
                            })
                        }


                    } else {

                        eventDetailViewModel.apiEventTaskCreate(event?.id.toString(), TaskPost(price.toString(), actionNameTextField?.editText?.text.toString(),
                                actionDescriptionTextField?.editText?.text.toString(), perPerson, assignee))

                        eventDetailViewModel.responseLiveDataCreateTask.observe(viewLifecycleOwner, Observer { response ->

                            when (response.status) {
                                Status.LOADING -> {
                                    showLoader()
                                }

                                Status.SUCCESS -> {
                                    hideLoader()

                                    //eventDetailViewModel.getEventData(eventId.toString())

                                    listner.AddActionFragmentFetch(position,task)

                                    //println("patch action is working fine without assignee")
                                }
                                Status.ERROR -> {
                                    hideLoader()

                                    showMessage(response.error?.message.toString())
                                    println(response.error?.message.toString())
                                }
                            }
                        })


                        *//*listner.AddActionFragmentFetch(newTask,task)
                        actionNameTextField.error = null*//*
                    }*/


                    println("for test purpose only ")

                    /*listner.AddActionFragmentFetch(position,task)
                    actionNameTextField.error = null*/
                    } else {

                        //actionNameTextField.error = "Action name is Required"
                        actionNameTextField.editText?.setError(getString(R.string.action_name_error))
                        actionNameTextField.requestFocus()
                        //Toast.makeText(activity,"Action name cannot be empty",Toast.LENGTH_SHORT).show()
                }
                hideLoader()
            }

            R.id.add_action_cancel -> {

                listner.cancelActionFragmentFetch()
            }
        }
    }

    interface AddActionFragmentListener {
        fun AddActionFragmentFetch(updatedPosition : Int, task : PostTasks)
        fun cancelActionFragmentFetch()
    }

    override fun AddSelectedAssignee (contact : Guests?, id : Int?) {
        val test1 = contact
        //Toast.makeText(activity , "" + test , Toast.LENGTH_SHORT).show()
        //assignParticipantButton.text = test
        rl_add_action_assignee_holder.visibility = View.VISIBLE
        txt_add_action_assignee_name.text = contact?.name
        txt_add_action_assignee_id.text = id.toString()
        assignee = id
    }

    override fun AddSelectedActivityParticipants(activityParticipants: ArrayList<Guests>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}