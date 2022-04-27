package com.batanks.nextplan.invitationstatus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.eventdetailsadmin.EventDetailViewAdmin
import com.batanks.nextplan.invitationstatus.adapters.InvitationStatusTabsAdapter
import kotlinx.android.synthetic.main.activity_invitation_status.*
import kotlinx.android.synthetic.main.edit_propriety.view.*

class InvitationStatus : BaseAppCompatActivity() {

    private var eventId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitation_status)

        eventId  = intent.getIntExtra("ID",0)

        val toolbar: Toolbar = findViewById(R.id.invitationstatusToolBar)
        setSupportActionBar(toolbar)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_header)

        invitationstatusToolBar.setNavigationOnClickListener {

            intent = Intent(this, EventDetailViewAdmin :: class.java)
            intent.putExtra("ID", eventId)
            intent.putExtra("FROM_HOME", false)
            startActivity(intent)
            finish()
        }

        val tabsPagerAdapter = InvitationStatusTabsAdapter(supportFragmentManager, eventId)
        view_pager.adapter = tabsPagerAdapter

        tabs.setupWithViewPager(view_pager)

        val tabOne = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabOne.text = getString(R.string.guests_caps)
        tabOne.setTextColor(resources.getColor(R.color.colorLittleBlue))
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_event_details_people_outline, 0, 0)
        tabs.getTabAt(0)?.customView = tabOne

        val tabTwo = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabTwo.text = getString(R.string.participants)
        tabTwo.setTextColor(resources.getColor(R.color.lightGreen))
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_event_details_participants_image, 0, 0)
        tabs.getTabAt(1)?.customView = tabTwo

        val tabThree = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabThree.text = getString(R.string.declin)
        tabThree.setTextColor(resources.getColor(R.color.pink))
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_declined_participant, 0, 0)
        tabs.getTabAt(2)?.customView = tabThree

        //tabs.getTabAt(0)?.text = getString(R.string.declin)
    }
}
