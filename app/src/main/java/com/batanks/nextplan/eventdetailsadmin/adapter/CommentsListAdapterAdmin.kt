package com.batanks.nextplan.eventdetailsadmin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.Comment
import kotlinx.android.synthetic.main.layout_comment_display.view.*

class CommentsListAdapterAdmin (val commentsList : ArrayList<Comment>, val context: Context, private val callBack: AddCommentsRecyclerViewCallBack): RecyclerView.Adapter<CommentsListAdapterAdmin.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_comment_display, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = commentsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val comment : Comment = commentsList[position]

        holder.comment.text = comment.comment
        holder.closeButtonIcon.setOnClickListener {

            commentsList.removeAt(position)
            callBack.closeButtonAddCommentItemListener(position)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val comment : TextView = itemView.comment
        val closeButtonIcon : ImageView = itemView.closeButtonIcon
    }

    interface AddCommentsRecyclerViewCallBack {
        fun closeButtonAddCommentItemListener(pos: Int)
    }

}