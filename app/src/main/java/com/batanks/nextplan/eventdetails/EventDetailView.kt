package com.batanks.nextplan.eventdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.adapter.VoteForDateMultipleListAdapter
import com.batanks.nextplan.eventdetails.adapter.VoteForPlaceMultipleListAdapter
import com.batanks.nextplan.eventdetails.dataclass.MultipleDateDisplay
import com.batanks.nextplan.eventdetails.dataclass.MultiplePlaceDisplay
import com.batanks.nextplan.eventdetails.fragment.*
import kotlinx.android.synthetic.main.activity_event_detail_view.*
import kotlinx.android.synthetic.main.layout_date_display.*

class EventDetailView : AppCompatActivity(), OnClickFunImplementation {

/*    val createVoteForDateFragment = CreateVoteForDateFragment()
    val createVoteForDateMultipleFragment = CreateVoteForDateMultipleFragment()
    val createVoteForDateMultipleListFragment = CreateVoteForDateMultipleListFragment()
    val createVoteForPlaceFragment = CreateVoteForPlaceFragment()
    val createVoteForPlaceMultipleFragment = CreateVoteForPlaceMultipleFragment()
    val createVoteForPlaceMultipleListFragment = CreateVoteForPlaceMultipleListFragment()
    val createEventFullDescriptionFragment = CreateEventFullDescriptionFragment()

    val fragmentManager = supportFragmentManager*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail_view)

        dateInit()
        placeInit()


        dateBackgroundMultiple.setOnClickListener {

            dateBackgroundMultiple.visibility = GONE

            dateDropDownBackgroundMultiple.visibility = VISIBLE
        }

        imgDateMultiLoader.setOnClickListener {

            dateDropDownBackgroundMultiple.visibility = GONE

            dateBackgroundMultiple.visibility = VISIBLE
        }

        placeBackgroundMulti.setOnClickListener {

            placeBackgroundMulti.visibility = GONE

            placeDropDownBackgroundMultiple.visibility = VISIBLE

        }

        imgPlaceMultiLoader.setOnClickListener {

            placeDropDownBackgroundMultiple.visibility = GONE

            placeBackgroundMulti.visibility = VISIBLE

        }

        totalCostBackground.setOnClickListener {

            totalCostBackground.visibility = GONE

            totalCostBackgroundFull.visibility = VISIBLE

        }

        totalCostBackgroundFull.setOnClickListener {

            totalCostBackgroundFull.visibility = GONE

            totalCostBackground.visibility = VISIBLE

        }

        eventInfoBackground.setOnClickListener {

            eventInfoBackground.visibility = GONE

            eventInfobackgroundMulti.visibility = VISIBLE

        }

        imgInfoMultiLoader.setOnClickListener {

            eventInfobackgroundMulti.visibility = GONE

            eventInfoBackground.visibility = VISIBLE

        }

        participantsListBackground.setOnClickListener {

            participantsListBackground.visibility = GONE

            participantsListBackgroundMulti.visibility = VISIBLE

        }

        imgParticipantsMultiLoader.setOnClickListener {

            participantsListBackgroundMulti.visibility = GONE

            participantsListBackground.visibility = VISIBLE

        }



        /*descriptionFrameBackground.setOnClickListener {

            totalCostBackground.visibility = GONE

            fragmentManager.beginTransaction().add(R.id.descriptionFrameBackground, createEventFullDescriptionFragment).addToBackStack(null).commit()
        }*/

        //addFragment()
    }

    fun dateInit(){

        val recyclerView = findViewById<RecyclerView>(R.id.VoteForDateMultipleRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dates = ArrayList<MultipleDateDisplay>()

        dates.add(MultipleDateDisplay(1,"First date","Thu, Mar 13 2019","Thu, Mar 13 2019 09:00 am",
                "Wed, Mar 14 2019 06:00 pm",10))
        dates.add(MultipleDateDisplay(2,"Second date","Fri, Mar 16 2019","Thu, Mar 16 2019 09:00 am",
                "Wed, Mar 17 2019 06:00 pm",20))
        dates.add(MultipleDateDisplay(3,"Third date","Sat, Mar 17 2019","Thu, Mar 17 2019 09:00 am",
                "Wed, Mar 18 2019 06:00 pm",30))
        /* dates.add(MultipleDateDisplay(4,"Fourth date","Sun, Mar 19 2019","Thu, Mar 19 2019 09:00 am",
                 "Wed, Mar 20 2019 06:00 pm",40))*/

        val adapter = VoteForDateMultipleListAdapter(dates,this)

        recyclerView.adapter = adapter

    }

    fun placeInit(){

        val recyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val places = ArrayList<MultiplePlaceDisplay>()

        places.add(MultiplePlaceDisplay(1,"First Place","Bangalore","People10, Bangalore",
                10))
        places.add(MultiplePlaceDisplay(2,"Second Place","Chennai","People10 Chennai",
                20))
        places.add(MultiplePlaceDisplay(3,"Third Place","Hyderabad","People10 Hyderabad",
                30))
        /*places.add(MultiplePlaceDisplay(4,"Fourth Place","Mumbai","People10 Mumbai",
                40))*/

        val adapter = VoteForPlaceMultipleListAdapter(places,this)

        recyclerView.adapter = adapter

    }

    /*fun addFragment() {

        var i = 1

        val fragtransaction = fragmentManager.beginTransaction()

        if (i == 0) {

            fragtransaction.add(R.id.dateFrameBackground, createVoteForDateFragment)
            fragtransaction.add(R.id.placeFrameBackground, createVoteForPlaceFragment)

        } else if (i == 1) {

            fragtransaction.add(R.id.dateFrameBackground, createVoteForDateMultipleFragment)
            fragtransaction.add(R.id.placeFrameBackground, createVoteForPlaceMultipleFragment)

            val dateView: View = findViewById(R.id.dateFrameBackground)
            addOnClickToDateView(dateView)

            val placeView: View = findViewById(R.id.placeFrameBackground)
            addOnClickToPlaceView(placeView)
        }

        fragtransaction.addToBackStack(null)

        fragtransaction.commit()
    }*/

    override fun addOnClickToDateView(view: View) {

        /*view.setOnClickListener {

            *//*val textview : TextView = view.findViewById(R.id.textviewVoteFor)
            addOnClickToDateBackView(textview)*//*

            *//*val textview : TextView = view.findViewById(R.id.textviewVoteFor)
            //addOnClickToDateBackView(textview)

            textview.setOnClickListener {

                Toast.makeText(this,"TextView is Clicked",Toast.LENGTH_SHORT).show()
                val viewtransaction = fragmentManager.beginTransaction()

                viewtransaction.replace(R.id.dateFrameBackground, createVoteForDateMultipleFragment).addToBackStack(null).commit()
            }*//*

            val viewtransaction = fragmentManager.beginTransaction()

            viewtransaction.replace(R.id.dateFrameBackground, createVoteForDateMultipleListFragment).addToBackStack(null).commit()

            val textview : TextView = view.findViewById(R.id.textviewVoteFor)
            addOnClickToDateBackView(textview)
        }*/
    }

    override fun addOnClickToDateBackView(view: View) {

        /*view.setOnClickListener {

            val viewtransaction = fragmentManager.beginTransaction()

            viewtransaction.replace(R.id.dateFrameBackground, createVoteForDateMultipleFragment).addToBackStack(null).commit()
        }*/
    }

    override fun addOnClickToPlaceView(view: View) {

        /*view.setOnClickListener {

            val viewtransaction = fragmentManager.beginTransaction()

            viewtransaction.replace(R.id.placeFrameBackground, createVoteForPlaceMultipleListFragment).addToBackStack(null).commit()
        }*/
    }

    override fun addOnClickToPlaceBackView(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOnClickToDescriptionBackView(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOnClickToActivityAssociatedBackView(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOnClickToEverybodyComeBackView(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOnClickCommentsBackView(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
