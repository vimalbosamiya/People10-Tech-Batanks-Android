package com.batanks.nextplan.notifications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.batanks.nextplan.R
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.notifications.adapters.NotificationsTabsAdapter
import kotlinx.android.synthetic.main.activity_notification.*

class Notification : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val toolbar: Toolbar = findViewById(R.id.notificationsToolBar)
        setSupportActionBar(toolbar)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_header)

        notificationsToolBar.setNavigationOnClickListener {

            intent = Intent(this, HomePlanPreview :: class.java)
            startActivity(intent)
            finish()
        }

        rl_notifications_category_bottom.setOnClickListener {

            intent = Intent(this, HomePlanPreview :: class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.bottom_up, R.anim.nothing)
            finish()
        }

        val tabsPagerAdapter = NotificationsTabsAdapter(supportFragmentManager)
        view_pager.adapter = tabsPagerAdapter

        tabs.setupWithViewPager(view_pager)

        val tabOne = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabOne.text = getString(R.string.all)
        tabOne.setTextColor(resources.getColor(R.color.colorWhite))
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_people_tablayout, 0, 0)
        tabs.getTabAt(0)?.customView = tabOne

        val tabTwo = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabTwo.text = getString(R.string._private)
        tabTwo.setTextColor(resources.getColor(R.color.colorWhite))
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_public_plan_tablayout, 0, 0)
        tabs.getTabAt(1)?.customView = tabTwo

        val tabThree = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabThree.text = getString(R.string._public)
        tabThree.setTextColor(resources.getColor(R.color.colorWhite))
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_private_plan_tablayout, 0, 0)
        tabs.getTabAt(2)?.customView = tabThree

        tabs.getTabAt(0)?.icon = getDrawable(R.drawable.ic_people_tablayout)
        tabs.getTabAt(1)?.icon = getDrawable(R.drawable.ic_public_plan_tablayout)
        tabs.getTabAt(2)?.icon = getDrawable(R.drawable.ic_private_plan_tablayout)

    }
}
