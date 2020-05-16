package com.batanks.nextplan.eventdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.adapter.VoteForDateMultipleListAdapter
import com.batanks.nextplan.eventdetails.dataclass.MultipleDateDisplay
import com.batanks.nextplan.eventdetails.fragment.*
import kotlinx.android.synthetic.main.activity_event_detail_view.*
import kotlinx.android.synthetic.main.layout_date_display.*

class EventDetailView : AppCompatActivity(), OnClickFunImplementation {

    val createVoteForDateFragment = CreateVoteForDateFragment()
    val createVoteForDateMultipleFragment = CreateVoteForDateMultipleFragment()
    val createVoteForDateMultipleListFragment = CreateVoteForDateMultipleListFragment()
    val createVoteForPlaceFragment = CreateVoteForPlaceFragment()
    val createVoteForPlaceMultipleFragment = CreateVoteForPlaceMultipleFragment()
    val createVoteForPlaceMultipleListFragment = CreateVoteForPlaceMultipleListFragment()
    val createEventFullDescriptionFragment = CreateEventFullDescriptionFragment()

    val fragmentManager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail_view)

        descriptionFrameBackground.setOnClickListener {

            totalCostBackground.visibility = GONE

            fragmentManager.beginTransaction().add(R.id.descriptionFrameBackground, createEventFullDescriptionFragment).addToBackStack(null).commit()
        }

        addFragment()
    }

    fun addFragment() {

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
    }

    override fun addOnClickToDateView(view: View) {

        view.setOnClickListener {

            /*val textview : TextView = view.findViewById(R.id.textviewVoteFor)
            addOnClickToDateBackView(textview)*/

            /*val textview : TextView = view.findViewById(R.id.textviewVoteFor)
            //addOnClickToDateBackView(textview)

            textview.setOnClickListener {

                Toast.makeText(this,"TextView is Clicked",Toast.LENGTH_SHORT).show()
                val viewtransaction = fragmentManager.beginTransaction()

                viewtransaction.replace(R.id.dateFrameBackground, createVoteForDateMultipleFragment).addToBackStack(null).commit()
            }*/

            val viewtransaction = fragmentManager.beginTransaction()

            viewtransaction.replace(R.id.dateFrameBackground, createVoteForDateMultipleListFragment).addToBackStack(null).commit()

            val textview : TextView = view.findViewById(R.id.textviewVoteFor)
            addOnClickToDateBackView(textview)
        }
    }

    override fun addOnClickToDateBackView(view: View) {

        view.setOnClickListener {

            val viewtransaction = fragmentManager.beginTransaction()

            viewtransaction.replace(R.id.dateFrameBackground, createVoteForDateMultipleFragment).addToBackStack(null).commit()
        }
    }

    override fun addOnClickToPlaceView(view: View) {

        view.setOnClickListener {

            val viewtransaction = fragmentManager.beginTransaction()

            viewtransaction.replace(R.id.placeFrameBackground, createVoteForPlaceMultipleListFragment).addToBackStack(null).commit()
        }
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
