package com.batanks.newplan.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.newplan.R
import com.batanks.newplan.home.adapter.HomePlanPreviewAdapter

class HomePlanPreview : AppCompatActivity() {

    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById(R.id.customToolBar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.homeScreenRecyclerView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(this)


        recyclerView?.adapter = HomePlanPreviewAdapter(listOf<String>())
    }
}