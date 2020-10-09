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
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.swagger.model.Task
import kotlinx.android.synthetic.main.fragment_add_action.*

class AddActionFragment (val listner : AddActionFragmentListener): BaseDialogFragment() , View.OnClickListener,
                         AssignPeopleFragment.AssignPeopleFragmentListner{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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
                requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(AssignPeopleFragment(this), AssignPeopleFragment::class.java.canonicalName)
                        .commitAllowingStateLoss()

            }

            R.id.ok -> {
                if (actionNameTextField?.editText?.length()!! >= 2) {

                    var perPerson : Boolean = false
                    var assignee : Int? = null

                    if (natureOfTheCostTextField.editText?.text.toString() == "Cost Per Person"){

                        perPerson = true

                    } else if (natureOfTheCostTextField.editText?.text.toString() == "Total Cost"){

                        perPerson = false
                    }

                    if (!TextUtils.isEmpty(txt_add_action_assignee_id.text.toString())){

                        assignee = txt_add_action_assignee_id.text.toString().toInt()
                    }
                    val task = Task(id = 0, price =  costActionTextField?.editText?.text.toString(),
                            name = actionNameTextField?.editText?.text.toString(),
                            description = actionDescriptionTextField?.editText?.text.toString(),
                            price_currency = natureOfTheCostTextField?.editText?.text.toString() ,
                            per_person = perPerson,
                            assignee = /*txt_add_action_assignee_name.text.toString())*/ /*txt_add_action_assignee_id.text.toString().toInt()*/ assignee,
                            assigneeName = txt_add_action_assignee_name.text.toString())

                   // println(txt_add_action_assignee_id.text.toString().toInt())
                    //println(assignee)
                   // println(natureOfTheCostTextField.editText?.text.toString())
                    //println(perPerson)

                            listner.AddActionFragmentFetch(task)
                            actionNameTextField.error = null
                    } else {

                        //actionNameTextField.error = "Action name is Required"
                        actionNameTextField.editText?.error = "Action name should contain atleast 2 characters"
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
        fun AddActionFragmentFetch(task :Task)
        fun cancelActionFragmentFetch()
    }

    override fun AddSelectedAssignee (contact : String, id : Int) {
        val test1 = contact
        //Toast.makeText(activity , "" + test , Toast.LENGTH_SHORT).show()
        //assignParticipantButton.text = test
        rl_add_action_assignee_holder.visibility = View.VISIBLE
        txt_add_action_assignee_name.text = contact
        txt_add_action_assignee_id.text = id.toString()
    }
}