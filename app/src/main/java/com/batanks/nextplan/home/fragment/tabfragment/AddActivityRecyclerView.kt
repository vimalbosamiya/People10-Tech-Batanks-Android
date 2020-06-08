package com.batanks.nextplan.home.fragment.tabfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.Activity
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_add_activity_item.view.*

class AddActivityRecyclerView (private val callBack: AddActivityRecyclerViewCallBack , private val modelList: ArrayList<Activity>)
: RecyclerView.Adapter<AddActivityRecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_add_activity_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val itemView = holder.itemView

        holder.txt_activity_name.text = modelList.get(position).title
        holder.txt_activity_location_name.text = modelList.get(position).place.name
        holder.activity_txt_date.text = modelList.get(position).date
        holder.activity_txt_time.text = modelList.get(position).date
        holder.activity_map.apply {
            onCreate(null)
            getMapAsync {
                val LATLNG = LatLng(modelList[position].place.latitude, modelList[position].place.longitude)
                with(it) {
                    onResume()
                    moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(LATLNG, 13f))
                    addMarker(com.google.android.gms.maps.model.MarkerOptions().position(LATLNG))
                }
            }
        }
        holder.activity_hideMapImg.setOnClickListener(View.OnClickListener {
            if(holder.activity_map.visibility == View.VISIBLE){
                holder.activity_map.visibility == View.GONE
            } else {
                holder.activity_map.visibility == View.VISIBLE
            }
        })
        holder.closeButton.setOnClickListener {
            modelList.removeAt(position)
            callBack.closeButtonAddActivityItemListener(position)
        }
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        //val contactName: TextView = item.txt_contact_name
        val closeButton: ImageView = item.activitycloseButtonIcon
        val activity_txt_date :TextView = item.activity_txt_date
        val activity_txt_time : TextView = item.activity_txt_time
        val txt_activity_name : TextView = item.txt_activity_name
        val txt_activity_location_name : TextView = item.txt_activity_location_name
        val activity_map : MapView = item.activity_MapView
        val activity_hideMapImg : ImageView = item.activity_hideMapImg
    }
    interface AddActivityRecyclerViewCallBack {
        fun closeButtonAddActivityItemListener(pos: Int)
    }
}