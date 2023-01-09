package com.batanks.nextplan.eventdetails.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.swagger.model.Comment
import com.batanks.nextplan.swagger.model.PostComments
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_comment_display.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommentsListAdapter (val commentsList : ArrayList<Comment> , val context : Context, private val callBack: AddCommentsRecyclerViewCallBack,
                           val userName : String?, private val eventDetailViewModel : EventDetailViewModel, private val eventId: Int): RecyclerView.Adapter<CommentsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_comment_display, parent, false)

        return  ViewHolder(view)
    }

    override fun getItemCount() =  commentsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setIsRecyclable(false)

        val comment : Comment = commentsList[position]

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
        val createdDate: Date = inputFormat.parse(comment.created)
        inputFormat.setTimeZone(TimeZone.getDefault())

        val outputFormat = SimpleDateFormat("EEE, MMM d yyyy HH:mm", Locale.ENGLISH)
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
        outputFormat.setTimeZone(TimeZone.getDefault())

        val formattedCreateDate = outputFormat.format(createdDate)

        if (comment.author == userName){

            holder.commentsSettings.visibility = View.VISIBLE

        } else if (comment.author != userName){

            holder.commentsSettings.visibility = View.GONE
        }

        holder.userName.text = comment.author
        holder.commentDateTime.text = formattedCreateDate
        holder.comment.text = comment.message
        holder.closeButtonIcon.visibility = View.GONE



        if (comment.user_status == "AC"){
            holder.commentUserIcon.setImageResource(R.drawable.ic_user_accepted)

        } else if(comment.user_status == "DN"){
            holder.commentUserIcon.setImageResource(R.drawable.ic_user_declined)

        } else if(comment.user_status == "PD"){
            holder.commentUserIcon.setImageResource(R.drawable.ic_user_pending)
        }

        holder.commentsSettings.setOnClickListener {

            editDialog(context, comment.id, eventId, comment.message)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userName : TextView = itemView.userName
        val commentDateTime : TextView = itemView.commentDateTime
        val comment : TextView = itemView.comment
        val closeButtonIcon : ImageView = itemView.commentsCloseButoon
        val commentsSettings : ImageView = itemView.commentsSettings
        val commentUserIcon : ImageView = itemView.commentUserIcon
    }

    private fun editDialog(context : Context, id : Int, eventId : Int, comment: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_edit_groups)
        val edit = dialog.findViewById(R.id.rl_edit_groups_edit) as RelativeLayout
        val delete = dialog.findViewById(R.id.rl_edit_groups_delete) as RelativeLayout

        edit.setOnClickListener {

            editCommentDialog(context,id,eventId,comment)
            dialog.dismiss()
        }
        delete.setOnClickListener {

            eventDetailViewModel.deleteComment(eventId.toString(), id.toString())
            dialog.dismiss()

        }
        dialog.show()
    }

    private fun editCommentDialog(context : Context, id : Int, eventId : Int, comment: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.fragment_add_comment)
        val tip_add_comment = dialog.findViewById(R.id.tip_add_comment) as TextInputLayout
        val btn_add_comment_ok = dialog.findViewById(R.id.btn_add_comment_ok) as MaterialButton
        val btn_add_comment_cancel = dialog.findViewById(R.id.btn_add_comment_cancel) as MaterialButton

        tip_add_comment?.editText?.setText(comment)

        btn_add_comment_ok.setOnClickListener {

            if (!tip_add_comment?.editText?.text.isNullOrEmpty()){

                eventDetailViewModel.editComment(eventId.toString(), id.toString(), PostComments(tip_add_comment?.editText?.text.toString()))

            } else if (tip_add_comment?.editText?.text.isNullOrEmpty()){

                tip_add_comment.editText?.setError(context.getString(R.string.comment_field_error))
                tip_add_comment.requestFocus()
            }

            dialog.dismiss()
        }
        btn_add_comment_cancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    interface AddCommentsRecyclerViewCallBack {
        fun closeButtonAddCommentItemListener(pos: Int)
    }
}