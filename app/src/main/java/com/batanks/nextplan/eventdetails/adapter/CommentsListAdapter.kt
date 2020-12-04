package com.batanks.nextplan.eventdetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.Comment
import kotlinx.android.synthetic.main.layout_comment_display.view.*

class CommentsListAdapter (val commentsList : ArrayList<Comment> , val context : Context, private val callBack: AddCommentsRecyclerViewCallBack): RecyclerView.Adapter<CommentsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_comment_display, parent, false)

        //view.commentsCloseButoon.visibility = GONE

        return  ViewHolder(view)
    }

    override fun getItemCount() =  commentsList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val comment : Comment = commentsList[position]

        holder.userName.text = comment.author

        holder.commentDateTime.text = comment.created

        holder.comment.text = comment.message

        println(comment.message)

        holder.closeButtonIcon.visibility = View.GONE

        /*holder.closeButtonIcon.setOnClickListener {

            commentsList.forEach{

                it.visibility = false

            }

            commentsList.removeAt(position)
            callBack.closeButtonAddCommentItemListener(position)
        }*/

        /*if (comment.visibility){

            holder.closeButtonIcon.visibility = View.VISIBLE
        }

        else{

            holder.closeButtonIcon.visibility = View.GONE

        }*/
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userName : TextView = itemView.userName
        val commentDateTime : TextView = itemView.commentDateTime
        val comment : TextView = itemView.comment
        val closeButtonIcon : ImageView = itemView.commentsCloseButoon


    }

    interface AddCommentsRecyclerViewCallBack {
        fun closeButtonAddCommentItemListener(pos: Int)
    }
}