package com.batanks.nextplan.home.fragment.action

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.home.markInRed
import com.batanks.nextplan.home.markRequiredInRed
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.search.adapters.CategoryAdapter
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.*
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_add_action.*
import kotlinx.android.synthetic.main.fragment_add_action_card.*
import kotlinx.android.synthetic.main.fragment_public_new_plan.*

class AddActionFragment (val listner : AddActionFragmentListener, private val position : Int, private val taskList : ArrayList<PostTasks>, private val event : Event?,
                         private val editButtonClicked : Boolean, private val userTask : Task?, private val updatedActionPosition : Int): BaseDialogFragment() , View.OnClickListener,
                         AssignPeopleFragment.AssignPeopleFragmentListner{

    private var taskId : Int? = 0

    private var isUserAssigned : Boolean = false
    private var assignee : Int? = null
    private var userName : String? = null
    private var defaultSelectedAssigne : String? = null

    var perPerson : Boolean = false
    var price : String = ""
    private var assigneeResponse : Task? = null

    private val eventDetailViewModel: EventDetailViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            context?.let {
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
        userName  = context?.getSharedPreferences("USER_DETAILS", AppCompatActivity.MODE_PRIVATE)?.getString("USERNAME",null)

        if(position >= 0){

            var natureOfCost : String? = null
            //taskId = taskList[position].id
            //txt_add_action_assignee_id.setText(taskList[position].assignee!!)
            actionNameEditText.setText(taskList[position].name)
            actionDescriptionEditText.setText(taskList[position].description)
            costActionEditText.setText(taskList[position].price.toString())
            if (taskList[position].per_person == true){ natureOfCost = "Cost Per Person" } else { natureOfCost = "Total Cost" }
            natureOfCostTextView.text = natureOfCost

            if (taskList.get(position).assignee != null){

                rl_add_action_assignee_holder.visibility = View.VISIBLE
                txt_add_action_assignee_name.text = taskList.get(position).assigneeName
                defaultSelectedAssigne = taskList.get(position).assigneeName
                assignee = taskList.get(position).assignee

               /* if (event!!.tasks?.get(position)?.assignee?.picture != null){

                    activity?.let { Glide.with(it).load(event!!.tasks?.get(position)?.assignee?.picture).circleCrop().into(img_add_action_assignee) }
                }*/
            }

        } else if (position == -2){

            actionNameEditText.isEnabled = false
            actionDescriptionEditText.isEnabled = false
            actionNameTextField.isEndIconVisible = false
            actionNameTextField.endIconMode = TextInputLayout.END_ICON_NONE
            assignParticipantButton.visibility = View.GONE
            assignMeButton.visibility = View.VISIBLE
            var natureOfCost : String? = null
            actionNameEditText.setText(userTask?.name)
            actionDescriptionEditText.setText(userTask?.description)
            costActionEditText.setText(userTask?.price.toString())
            if (userTask?.per_person == true){ natureOfCost = "Cost Per Person" } else { natureOfCost = "Total Cost" }
            natureOfCostTextView.text = natureOfCost

            if (userTask?.assignee != null){
                assignMeButton.visibility = View.GONE
                assignParticipantHolder.visibility = View.GONE
                txt_addAction_assignee_who_with.visibility = View.GONE
                view_seperator_whowith_tetx.visibility = View.GONE
                rl_add_action_assignee_holder.visibility = View.VISIBLE
                txt_add_action_assignee_name.text = userTask.assignee.username
                isUserAssigned = true
            }
        }

        eventDetailViewModel.responseLiveDataAssignAction.observe(viewLifecycleOwner, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    assigneeResponse = response.data as Task

                    eventDetailViewModel.apiEventTaskPatch(id.toString(),assigneeResponse?.id.toString(),
                        TaskPatch(price, assigneeResponse?.name, assigneeResponse?.description, perPerson, assigneeResponse!!.assignee!!.id.toString()))
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        eventDetailViewModel.responseLiveDataTaskPatch.observe(viewLifecycleOwner, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    assigneeResponse = response.data as Task
                    listner.addActionFragmentFetch(updatedActionPosition, null, assigneeResponse)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        view.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if (event?.action == MotionEvent.ACTION_DOWN) {

                    val imm = v?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    v.clearFocus()
                }

                return false
            }
        })

        assignParticipantButton.setOnClickListener(this)
        assignMeButton.setOnClickListener(this)
        img_add_action_assignee_dots.setOnClickListener(this)
        ok.setOnClickListener(this)
        add_action_cancel.setOnClickListener(this)
        natureOfTheCostTextField.setOnClickListener(this)

        //loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)
    }

    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.assignParticipantButton -> {

                if (editButtonClicked == true){

                    requireActivity().supportFragmentManager
                       .beginTransaction()
                       .add(AssignPeopleFragment(this, event, true, null, defaultSelectedAssigne), AssignPeopleFragment::class.java.canonicalName)
                       .commitAllowingStateLoss()

                }else {

                    Toast.makeText(activity,getString(R.string.add_assignee_toast),Toast.LENGTH_LONG).show()
                }
            }

            R.id.assignMeButton -> {

                assignMeButton.visibility = View.GONE
                assignParticipantHolder.visibility = View.GONE
                txt_addAction_assignee_who_with.visibility = View.GONE
                view_seperator_whowith_tetx.visibility = View.GONE
                rl_add_action_assignee_holder.visibility = View.VISIBLE
                txt_add_action_assignee_name.text = userName
                isUserAssigned = true
            }

            R.id.ok -> {

                if (actionNameTextField?.editText?.length()!! >= 2) {

                    if (natureOfCostTextView.text.toString() == "Cost Per Person"){ perPerson = true }
                    else if (natureOfCostTextView.text.toString() == "Total Cost"){ perPerson = false }

                    if (!TextUtils.isEmpty(costActionTextField?.editText?.text.toString())){
                        price = costActionTextField?.editText?.text.toString() }

                    val task = PostTasks( /*id = taskId,*/
                            price =  price,
                            name = actionNameTextField?.editText?.text.toString(),
                            description = actionDescriptionTextField?.editText?.text.toString(),
                            per_person = perPerson,
                            assignee =  assignee,
                            assigneeName = txt_add_action_assignee_name.text.toString())

                    if (position == -2){

                        /*if (isUserAssigned){

                            eventDetailViewModel.assignAction(event?.id.toString(), AssignTask(userTask?.id!!))

                        } else if (!isUserAssigned){

                            eventDetailViewModel.apiEventTaskPatch(id.toString(),userTask?.id.toString(),
                                TaskPatch(price , userTask?.name, userTask?.description, perPerson, ""))
                        }*/

                        if (userTask?.assignee == null && rl_add_action_assignee_holder.isVisible){

                            eventDetailViewModel.assignAction(event?.id.toString(), AssignTask(userTask?.id!!))

                        }else if(userTask?.assignee != null && !rl_add_action_assignee_holder.isVisible){

                            eventDetailViewModel.apiEventTaskPatch(id.toString(), userTask.id.toString(),
                                TaskPatch(price , userTask.name, userTask.description, perPerson, ""))

                        } else if(userTask?.assignee != null && rl_add_action_assignee_holder.isVisible){

                            eventDetailViewModel.apiEventTaskPatch(id.toString(), userTask.id.toString(),
                                TaskPatch(price, userTask.name,
                                    userTask.description, perPerson, userTask.assignee.id.toString()))

                        } else {  Toast.makeText(activity,"You must assign yourself first",Toast.LENGTH_LONG).show() }

                    } else if (editButtonClicked){

                        if (position >= 0){
                            if (assignee!= null){ listner.addActionFragmentFetch(position,task, null) }
                            else if (assignee == null) { listner.addActionFragmentFetch(position,task, null) }
                        } else { listner.addActionFragmentFetch(position,task, null) }

                    }else {
                       listner.addActionFragmentFetch(position, task, null)
                    }

                } else {

                    actionNameTextField.editText?.error = getString(R.string.action_name_error)
                        actionNameTextField.requestFocus()
                }

            }

            R.id.add_action_cancel -> {

                listner.cancelActionFragmentFetch()
            }

            R.id.img_add_action_assignee_dots -> {

                if (position == -2){

                    assignMeButton.visibility = View.VISIBLE
                    assignParticipantHolder.visibility = View.VISIBLE
                    isUserAssigned = false
                }

                rl_add_action_assignee_holder.visibility = View.GONE
                txt_add_action_assignee_name.text = ""
                assignee = null
                defaultSelectedAssigne = null
            }

            R.id.natureOfTheCostTextField -> {

                context?.let { showNatureOfCostDialog(it) }
            }
        }
    }

    private fun showNatureOfCostDialog(context : Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.nature_of_cost_pop_up)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val perPersonTextView = dialog.findViewById(R.id.perPersonTextView) as TextView
        val totalCostTextView = dialog.findViewById(R.id.totalCostTextView) as TextView

        perPersonTextView.setOnClickListener {

            natureOfCostTextView.setText(R.string.cost_per_person)
            natureOfCostIcon.setImageResource(R.drawable.ic_cost_perperson_icon)
            dialog.dismiss()
        }

        totalCostTextView.setOnClickListener {

            natureOfCostTextView.setText(R.string.total_cost)
            natureOfCostIcon.setImageResource(R.drawable.ic_action_cost_icon)
            dialog.dismiss()
        }

        dialog.show()

        dialog.window?.decorView?.setOnTouchListener { v, event ->

            if (event?.action == MotionEvent.ACTION_DOWN) {

                val imm = v?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                v.clearFocus()
            }
            false
        }
    }

    interface AddActionFragmentListener {
        fun addActionFragmentFetch(updatedPosition : Int, task : PostTasks?, taskResponse : Task?)
        fun cancelActionFragmentFetch()
    }

    override fun AddSelectedAssignee (contact : Guests?, id : Int?) {
        val test1 = contact
        //Toast.makeText(activity , "" + test , Toast.LENGTH_SHORT).show()
        //assignParticipantButton.text = test
        rl_add_action_assignee_holder.visibility = View.VISIBLE
        txt_add_action_assignee_name.text = contact?.user?.username
        defaultSelectedAssigne = contact?.user?.username
        txt_add_action_assignee_id.text = id.toString()
        assignee = id
    }

    override fun AddSelectedActivityParticipants(activityParticipants: ArrayList<Guests>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}