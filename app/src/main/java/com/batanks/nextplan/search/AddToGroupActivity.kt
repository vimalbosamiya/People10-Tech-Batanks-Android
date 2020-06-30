package com.batanks.nextplan.search

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.view.Window
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.search.adapters.AddToGroupsAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_add_to_groups.*

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
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_create_group)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btn_create_group_cancel = dialog.findViewById(R.id.btn_create_group_cancel) as Button
        val btn_create_group_ok = dialog.findViewById(R.id.btn_create_group_ok) as Button

        btn_create_group_cancel.setOnClickListener {
            dialog.dismiss()
        }
        btn_create_group_ok.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    override fun onClick(v: View?) {
        
    }
}