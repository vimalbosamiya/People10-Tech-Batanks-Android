package com.batanks.nextplan.home.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.EventDetailView
import com.batanks.nextplan.eventdetailsadmin.EventDetailViewAdmin
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.home.ModelPreferencesManager
import com.batanks.nextplan.swagger.model.EventList
import com.batanks.nextplan.swagger.model.GetEventListHome
import com.batanks.nextplan.swagger.model.User
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_event_name.view.*

class HomePlanPreviewAdapter(private val myList: List<GetEventListHome>) : RecyclerView.Adapter<HomePlanPreviewAdapter.MyViewHolder>() {

    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_event_name, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //holder.setIsRecyclable(false)

        val userData = ModelPreferencesManager.get<User>("USER_DATA")
        val userId = userData?.id

        val event : GetEventListHome = myList[position]

        holder.eventName.text = event.title
        holder.fromResponseTextView.text = event.date.start
        holder.toResponseTextView.text = event.date.end

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

        } else if (event.creator.id == userId && event.draft == false){

            holder.eventItem.setBackgroundColor(Color.parseColor("#3D473D"))
            holder.body.setBackgroundColor(Color.parseColor("#3D473D"))
            holder.header.setBackgroundResource(R.drawable.ic_event_own_plan_item_background)
        }

        if(event.status == "AC" && event.private == true){

            holder.eventPrivateCategoryIcon.setImageResource(R.drawable.ic_privateeventcategoryacceptedicon)

        } else if(event.status == "AC" && event.private == false) {

            holder.eventPrivateCategoryIcon.setImageResource(R.drawable.ic_publiceventcategoryiconaccepted)

        }

        else if (event.status == "DN" && event.private == false){

            holder.eventPrivateCategoryIcon.setImageResource(R.drawable.ic_publiceventcategoryicondeclined)

        } else if (event.status == "DN" && event.private == true){

            holder.eventPrivateCategoryIcon.setImageResource(R.drawable.ic_publiceventcategoryicondeclined)
        }

            if (myList[position].status == "pending"){

                holder.eventItem.setBackgroundColor(Color.parseColor("#232323"))

            } else if (isEmpty(myList[position].status)){

                holder.eventItem.setBackgroundColor(Color.parseColor("#3D473D"))
            }

        val placeName = event.place?.name
        val placeCity = event.place?.city
        val address = event.place?.address
        val placeCountry = event.place?.country
        val placeZipcode = event.place?.zipcode

        val stringBuilder = StringBuilder()
                .append(placeName)

                .append(" ")
                .append(address)

                .append(" ")
                .append(placeCity)

                .append(" ")
                .append(placeCountry)

                .append(" ")
                .append(placeZipcode)

        holder.placeResponseTextView.text = stringBuilder.toString()

        holder.organizerName.text = event.creator.username
        Glide.with(context).load(event.creator.picture).circleCrop().into(holder.userIcon)
        holder.eventNameFull.text = event.title
        holder.eventCategory.text = event.category.name
        Glide.with(context).load(event.category.picture).circleCrop().into(holder.eventPrivateCategoryIcon)
        Glide.with(context).load(event.category.picture).circleCrop().into(holder.eventPrivateCategoryIconFull)
        holder.eventDescription.text = event.detail
        holder.fromResponseTextViewFull.text = event.date?.start
        holder.toResponseTextViewFull.text = event.date?.end
        holder.placeResponseTextViewFull.text = stringBuilder.toString()

        holder.eventItem.setOnClickListener {

            holder.eventItem.visibility = GONE
            println(position)
            holder.eventItemFull.visibility = VISIBLE
            //notifyDataSetChanged()
            /*it.visibility = GONE
            it?.eventItemFull?.visibility = VISIBLE*/
        }

        holder.eventItemFull.setOnClickListener {

            holder.eventItem.visibility = VISIBLE

            holder.eventItemFull.visibility = GONE

        }

        holder.eventNextButton.setOnClickListener(View.OnClickListener {
            if(position == 0){
                val intent = Intent(context, EventDetailView::class.java)
                intent.putExtra("ID", myList.get(position).pk)
                startActivity(context,intent,null)

            } else {
                val intent = Intent(context, EventDetailViewAdmin::class.java)
                startActivity(context,intent,null)
            }
        })
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val eventName : TextView = item.eventName
        val fromResponseTextView : TextView = item.fromResponseTextView
        val toResponseTextView : TextView = item.toResponseTextView
        val placeResponseTextView : TextView = item.placeResponseTextView
        val from: TextView = item.fromResponseTextView
        val to: TextView = item.toResponseTextView
        val eventItem : ConstraintLayout = item.eventItem
        val eventItemFull : ConstraintLayout = item.eventItemFull
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

    }
}