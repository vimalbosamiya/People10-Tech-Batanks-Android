package com.batanks.nextplan.home.fragment.action

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
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
import android.widget.EditText
import android.widget.Toast
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.swagger.model.Task
import kotlinx.android.synthetic.main.fragment_add_action.*

class AddActionFragment (val listner : AddActionFragmentListener): BaseDialogFragment() , View.OnClickListener
                        , AssignPeopleFragment.AssignPeopleFragmentListner{

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

        view.setOnTouchListener(object : View.OnTouchListener {

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
                if (!TextUtils.isEmpty(actionNameTextField?.editText?.text.toString())) {
                    val task = Task(id = 0, price = natureOfTheCostTextField?.editText?.text.toString(),
                            name = actionNameTextField?.editText?.text.toString(),
                            description = actionDescriptionTextField?.editText?.text.toString(),
                            price_currency = costActionTextField?.editText?.text.toString(),
                            per_person = false,
                            assignee = 0)
                            listner.AddActionFragmentFetch(task)
                            actionNameTextField.error = null
                    } else {

                    if(TextUtils.isEmpty(actionNameTextField?.editText?.text.toString())){

                        //actionNameTextField.error = "Action name is Required"
                        actionNameTextField.editText?.error = "Action name is Required"
                        //Toast.makeText(activity,"Action name cannot be empty",Toast.LENGTH_SHORT).show()
                    }
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
    override fun AddSelectedAssignee ( test : String) {
        val test1 = test
        //Toast.makeText(activity , "" + test , Toast.LENGTH_SHORT).show()
        //assignParticipantButton.text = test
        rl_add_action_assignee_holder.visibility = View.VISIBLE
        txt_add_action_assignee_name.text = test
    }
}