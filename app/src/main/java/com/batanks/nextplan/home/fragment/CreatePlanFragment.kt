package com.batanks.nextplan.home.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.home.fragment.tabfragment.TabsPagerAdapter
import com.batanks.nextplan.home.fragment.tabfragment.privateplan.PrivatePlanFragment
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.PublicPlanFragment
import com.batanks.nextplan.swagger.model.Empty
import com.batanks.nextplan.swagger.model.Event
import com.batanks.nextplan.swagger.model.GetEventListHome
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_event_detail_view_admin.*
import kotlinx.android.synthetic.main.fragment_add_new_plan.*
import kotlinx.android.synthetic.main.fragment_add_new_plan.tabs
import kotlinx.android.synthetic.main.fragment_add_new_plan.view_pager

class CreatePlanFragment (private val fromNotifications : Boolean, private val fromSearch : Boolean, private val isPrivate : Boolean, private val draft : Boolean, private val eventId : Int?, private val editButtonClicked : Boolean, private val deleteButtonClicked : Boolean,
                          private val listener : PublicPlanFragment.PublicPlanFragmentListener?, private val privateListener : PrivatePlanFragment.PrivatePlanFragmentListener?): BaseFragment() {


    //lateinit var arr : ArrayList<Empty>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_new_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

         //arr.add(Empty(""))

        val ref = requireActivity() as AppCompatActivity
        ref.setSupportActionBar(toolBar)
        ref.supportActionBar?.setTitle(R.string.add_plan)
        ref.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ref.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_header)

        toolBar.setNavigationOnClickListener {

            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()

            if (editButtonClicked == false && deleteButtonClicked == false && fromSearch == false && fromNotifications == false){

                homeScreenItemsVisible()

            } else if (fromSearch == true){

                activity?.searchAppBarLayout?.visibility = View.VISIBLE

            } else if ((editButtonClicked == true || deleteButtonClicked == true) && fromSearch == false){

                adminDetailViewItemsVisible()

            }else if (fromNotifications == true){

                notificationItemsVisible()
            }

            //Toast.makeText(activity,"Back Button Working from Navigation" , Toast.LENGTH_SHORT).show()
        }

        val tabsPagerAdapter = TabsPagerAdapter(fromSearch, childFragmentManager, draft, eventId, editButtonClicked, deleteButtonClicked, listener, privateListener)
        view_pager.adapter = tabsPagerAdapter

        if (isPrivate == false){

            view_pager.setCurrentItem(1,true)
        }


        tabs.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"))

        tabs.setupWithViewPager(view_pager)

        val tabOne = LayoutInflater.from(view.context).inflate(R.layout.custom_tab, null) as TextView
        tabOne.text = getString(R.string._private)
        tabOne.setTextColor(resources.getColor(R.color.colorWhite))
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_private_plan_tablayout, 0, 0)
        tabs.getTabAt(0)?.customView = tabOne

        val tabTwo = LayoutInflater.from(view.context).inflate(R.layout.custom_tab, null) as TextView
        tabTwo.text = getString(R.string._public)
        tabTwo.setTextColor(resources.getColor(R.color.colorWhite))
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_public_plan_tablayout, 0, 0)
        tabs.getTabAt(1)?.customView = tabTwo

        tabs.getTabAt(0)?.icon = ref.getDrawable(R.drawable.ic_private_plan_tablayout)
        tabs.getTabAt(1)?.icon = ref.getDrawable(R.drawable.ic_public_plan_tablayout)
    }



    companion object {
        const val TAG = "CreatePlanFragment"
    }
    override fun onDestroy() {
        super.onDestroy()
        ///activity?.extFab!!.visibility = View.VISIBLE             //uncomment
    }

    private fun homeScreenItemsVisible(){

        activity?.appBarLayout?.visibility = View.VISIBLE
        activity?.extFab!!.visibility = View.VISIBLE
        activity?.search!!.visibility = View.VISIBLE
        activity?.notification!!.visibility = View.VISIBLE
        activity?.img_settings!!.visibility = View.VISIBLE
    }

    private fun notificationItemsVisible(){

        activity?.notificationAppBarLayout?.visibility = View.VISIBLE
        activity?.rl_notifications_category_bottom?.visibility = View.VISIBLE
    }

    private fun adminDetailViewItemsVisible(){

        activity?.customToolBar?.visibility = View.VISIBLE
    }
}