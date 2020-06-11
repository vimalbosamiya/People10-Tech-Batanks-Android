package com.batanks.nextplan.home.fragment.action

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        Toast.makeText(activity , "" + test , Toast.LENGTH_SHORT).show()
        //assignParticipantButton.text = test
        rl_add_action_assignee_holder.visibility = View.VISIBLE
    }
}