package com.batanks.nextplan.search

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.View.VISIBLE
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.search.adapters.AddToGroupsAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_add_to_groups.*
import kotlinx.android.synthetic.main.layout_create_group.*

class AddToGroupActivity : BaseAppCompatActivity(), View.OnClickListener  {

    lateinit var groupsRecyclerView: RecyclerView
    lateinit var groupAdapter: AddToGroupsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add_to_groups)

        groupsRecyclerView = findViewById(R.id.groupsRecyclerView)
        groupsRecyclerView.layoutManager = LinearLayoutManager(this)

        groupsRecyclerView.adapter = AddToGroupsAdapter(listOf<String>())

        backArrow.setOnClickListener {

            /*intent = Intent(this, HomePlanPreview::class.java)

            startActivity(intent)

            supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayout, SearchFragment())
                    .addToBackStack(SearchFragment.TAG).commit()

            frameLayout.visibility = VISIBLE*/

            finish()
        }

        rl_settings_create_new_groups.setOnClickListener {

            showDialog()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    /*private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_create_followups)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btn_create_followups_cancel = dialog.findViewById(R.id.btn_create_followups_cancel) as Button
        val btn_create_followups_ok = dialog.findViewById(R.id.btn_create_followups_ok) as Button

        btn_create_followups_cancel.setOnClickListener {
            dialog.dismiss()
        }
        btn_create_followups_ok.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }*/


    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_create_group)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btn_create_group_cancel = dialog.findViewById(R.id.btn_create_group_cancel) as Button
        val btn_create_group_ok = dialog.findViewById(R.id.btn_create_group_ok) as Button

        dialog.show()

        btn_create_group_ok.setOnClickListener {

            if (input_create_group_gname != null){

                Toast.makeText(this,"Empty",Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this,"Not empty",Toast.LENGTH_SHORT).show()
            }

            /*if(!TextUtils.isEmpty(tip_create_group_gname?.editText?.text.toString())){

                // dialog.dismiss()
                Toast.makeText(this,"Condition Running",Toast.LENGTH_SHORT).show()

            }else if(TextUtils.isEmpty(tip_create_group_gname?.editText?.text.toString())){

                Toast.makeText(this,"Came to error",Toast.LENGTH_SHORT).show()

                tip_create_group_gname.editText?.error = "UserName is Required"
                tip_create_group_gname.editText?.requestFocus()
            }*/

        }

        btn_create_group_cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.decorView?.setOnTouchListener { v, event ->

            if (event?.action == MotionEvent.ACTION_DOWN) {

                val imm = v?.getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                v.clearFocus()
            }
            false
        }

    }

    override fun onClick(v: View?) {

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
}