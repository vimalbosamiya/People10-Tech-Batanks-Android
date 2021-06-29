package com.batanks.nextplan.home.adapter

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.EventDetailView
import com.batanks.nextplan.eventdetailsadmin.EventDetailViewAdmin
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.home.fragment.CreatePlanFragment
import com.batanks.nextplan.swagger.model.GetEventListHome
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_event_name.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class HomePlanPreviewAdapter(private val myList: ArrayList<GetEventListHome>/*, val listener: HomePlanPreviewAdapterListener */) : RecyclerView.Adapter<HomePlanPreviewAdapter.MyViewHolder>() {

    lateinit var context : Context
    private var userId : Int = 0
    private var userName : String? = null
    private lateinit var mRecyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    fun scrollToPosition(position: Int){
        mRecyclerView.scrollToPosition(position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        context = parent.context
        val mView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_event_name, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.setIsRecyclable(false)

        userId  = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).getInt("ID",0)
        userName = context.getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("USERNAME",null)

        val event : GetEventListHome = myList[position]

        holder.eventName.text = event.title

        var startDate : Date?
        var formattedStartDate : String? = null
        var endDate : Date?
        var formattedEndDate : String? = null

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = SimpleDateFormat("EEE, MMM d yyyy HH:mm")

        if (event.date?.start != null){

            startDate = inputFormat.parse(event?.date?.start)
            formattedStartDate = outputFormat.format(startDate)
        } else {}

        if (event.date?.end != null){

            endDate = inputFormat.parse(event?.date?.end)
            formattedEndDate = outputFormat.format(endDate)
        }else {}

        holder.fromResponseTextView.text = formattedStartDate
        holder.toResponseTextView.text = formattedEndDate
        holder.fromResponseTextViewFull.text = formattedStartDate
        holder.toResponseTextViewFull.text = formattedEndDate

        if (event.category?.picture != null){

            Glide.with(context).load(event.category.picture).circleCrop().into(holder.eventPrivateCategoryIcon)
            Glide.with(context).load(event.category.picture).circleCrop().into(holder.eventPrivateCategoryIconFull)
        }

        if (event.private == true){

            holder.privateIcon.visibility = VISIBLE

        } else {

            holder.privateIcon.visibility = GONE
        }

        if(event.creator.id == userId && event.draft == true){

            holder.eventItem.setBackgroundColor(Color.parseColor("#39444E"))
            holder.body.setBackgroundColor(Color.parseColor("#39444E"))
            holder.header.setBackgroundResource(R.drawable.ic_event_draft_item_background)
            holder.eventNextButton.visibility = GONE
            holder.eventEditButton.visibility = VISIBLE

        } else if (event.creator.id != userId){

            holder.eventItem.setBackgroundColor(Color.parseColor("#232323"))
            holder.body.setBackgroundColor(Color.parseColor("#232323"))
            holder.header.setBackgroundResource(R.drawable.ic_planpreviewbackground)

        } else if(event.creator.id == userId && event.draft == false) {

            holder.eventItem.setBackgroundColor(Color.parseColor("#3D473D"))
            holder.body.setBackgroundColor(Color.parseColor("#3D473D"))
            holder.header.setBackgroundResource(R.drawable.ic_event_own_plan_item_background)
        }

        if(event.status == "AC" && event.private == true){

            holder.eventStatus.setImageResource(R.drawable.ic_privateeventcategoryacceptedicon)
            holder.eventStatusFull.setImageResource(R.drawable.ic_privateeventcategoryacceptedicon)

        } else if(event.status == "AC" && event.private == false) {

            holder.eventStatus.setImageResource(R.drawable.ic_publiceventcategoryiconaccepted)
            holder.eventStatusFull.setImageResource(R.drawable.ic_publiceventcategoryiconaccepted)

        } else if (event.status == "DN" && event.private == false){

            holder.eventStatus.setImageResource(R.drawable.ic_publiceventcategoryicondeclined)
            holder.eventStatusFull.setImageResource(R.drawable.ic_publiceventcategoryicondeclined)

        } else if (event.status == "DN" && event.private == true){

            holder.eventStatus.setImageResource(R.drawable.ic_private_event_category_icon_declined)
            holder.eventStatusFull.setImageResource(R.drawable.ic_private_event_category_icon_declined)

        } else if (event.status == null && event.private == true){

            holder.eventStatus.setImageResource(R.drawable.ic_eventprivatecategoryicon)
            holder.eventStatusFull.setImageResource(R.drawable.ic_eventprivatecategoryicon)
        }

        val placeName : String?
        val placeCity : String?
        val address : String?
        val placeCountry : String?
        val placeZipcode : String?

        if (!event.place?.name.isNullOrEmpty()){

             placeName = event.place?.name

        } else {

            placeName = ""
        }

        if (!event.place?.city.isNullOrEmpty()){

            placeCity = event.place?.city

        } else {

            placeCity = ""
        }

        if (!event.place?.address.isNullOrEmpty()){

            address = event.place?.address

        } else {

            address = ""
        }

        if (!event.place?.country.isNullOrEmpty()){

            placeCountry = event.place?.country

        } else {

            placeCountry = ""
        }

        if (!event.place?.zipcode.isNullOrEmpty()){

            placeZipcode = event.place?.zipcode

        } else {

            placeZipcode = ""
        }

        /*val placeName = event.place?.name
        val placeCity = event.place?.city
        val address = event.place?.address
        val placeCountry = event.place?.country
        val placeZipcode = event.place?.zipcode*/

        val stringBuilder = StringBuilder()
                .append(placeName)

                .append(" ")
                .append(address)

                .append(" ")
                .append(placeCity)

                .append("\n")

                .append(" ")
                .append(placeZipcode)

                .append(" ")
                .append(placeCountry)

       /* if (stringBuilder.toString() != null){

            holder.placeResponseTextView.text = stringBuilder.toString()
            holder.placeResponseTextViewFull.text = stringBuilder.toString()

        } else {}*/

        holder.placeResponseTextView.text = event.place?.name
        holder.placeResponseTextViewFull.text = event.place?.name

        event.place?.name

        /*holder.placeResponseTextView.text = stringBuilder.toString()
        holder.placeResponseTextViewFull.text = stringBuilder.toString()*/

        holder.organizerName.text = event.creator.username
        Glide.with(context).load(event.creator.picture).circleCrop().into(holder.userIcon)
        holder.eventNameFull.text = event.title
        holder.eventCategory.text = event.category?.name

        holder.eventDescription.text = event.detail

        holder.eventItem.setOnClickListener {

            myList.forEach {

                it.isExpanded = false
            }

            myList[position] .isExpanded = true

            holder.eventItem.visibility = GONE

            holder.eventItemFull.visibility = VISIBLE

            holder.eventItemHolder.requestFocus()

            scrollToPosition(position)
        }

       if(myList[position].isExpanded) {

           holder.eventItem.visibility = GONE

           holder.eventItemFull.visibility = VISIBLE
       }

        holder.eventItemFull.setOnClickListener {

            holder.eventItem.visibility = VISIBLE

            holder.eventItemFull.visibility = GONE

        }

        holder.eventNextButton.setOnClickListener {

            if (event.creator.username == userName){

                val intent = Intent(context, EventDetailViewAdmin::class.java)
                intent.putExtra("ID", myList.get(position).pk)
                intent.putExtra("FROM_HOME", true)
                startActivity(context,intent,null)
                (context as Activity).finish()

            } else if (event.creator.username != userName){

                val intent = Intent(context, EventDetailView::class.java)
                intent.putExtra("ID", myList.get(position).pk)
                intent.putExtra("FROM_HOME", true)
                startActivity(context,intent,null)
                (context as Activity).finish()
            }
        }

        /* holder.eventNextButton.setOnClickListener(View.OnClickListener {
            if(position == 0){
                val intent = Intent(context, EventDetailView::class.java)
                intent.putExtra("ID", myList.get(position).pk)
                startActivity(context,intent,null)
                (context as HomePlanPreview).finish()

            } else {
                val intent = Intent(context, EventDetailViewAdmin::class.java)
                intent.putExtra("ID", myList.get(position).pk)
                startActivity(context,intent,null)
                (context as HomePlanPreview).finish()
            }
        })*/

        holder.eventEditButton.setOnClickListener {

            val draft: Boolean = true

            (context as HomePlanPreview).supportFragmentManager.beginTransaction()
                            .add(R.id.frameLayout, CreatePlanFragment(draft, event.pk, false, false,null), CreatePlanFragment::class.java.canonicalName).commitAllowingStateLoss()

            (context as HomePlanPreview).frameLayout.visibility = VISIBLE
            (context as HomePlanPreview). appBarLayout.visibility = GONE
            (context as HomePlanPreview).extFab.visibility = View.GONE

            println("edit being clicked")

            //listener.openPlanFragment(event)
        }
    }

    inner class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val eventName : TextView = item.eventName
        val fromResponseTextView : TextView = item.fromResponseTextView
        val toResponseTextView : TextView = item.toResponseTextView
        val placeResponseTextView : TextView = item.placeResponseTextView
        val from: TextView = item.fromResponseTextView
        val to: TextView = item.toResponseTextView
        val eventItem : ConstraintLayout = item.eventItem
        val eventItemFull : ConstraintLayout = item.eventItemFull
        val eventItemHolder : ConstraintLayout = item.eventItemHolder
        val header : ImageView = item.header
        val body : ImageView = item.body
        val eventNextButton : ImageView = item.eventNextButton
        val eventEditButton : ImageView = item.eventEditButton
        val eventNameFull : TextView = item.eventNameFull
        val eventCategory : TextView = item.eventCategory
        val eventDescription : TextView = item.eventDescription
        val fromResponseTextViewFull : TextView = item.fromResponseTextViewFull
        val toResponseTextViewFull : TextView = item.toResponseTextViewFull
        val placeResponseTextViewFull : TextView = item.placeResponseTextViewFull
        val organizerName : TextView = item.organizerName
        val userIcon : ImageView = item.userIcon
        val eventPrivateCategoryIconFull : ImageView = item.eventPrivateCategoryIconFull
        val privateIcon : ImageView = item.privateIcon
        val eventPrivateCategoryIcon : ImageView = item.eventPrivateCategoryIcon
        val eventStatus : ImageView = item.eventStatus
        val eventStatusFull : ImageView = item.eventStatusFull
        val eventFrameLayout : FrameLayout = item.eventFrameLayout

    }

    interface HomePlanPreviewAdapterListener {

        //fun openPlanFragment(getEventListHome : GetEventListHome)
        fun itemPosition (pos : Int)
    }
}