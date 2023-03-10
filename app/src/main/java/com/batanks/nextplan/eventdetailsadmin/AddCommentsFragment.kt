package com.batanks.nextplan.eventdetailsadmin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.swagger.model.Comment
import com.batanks.nextplan.swagger.model.PostComments
import kotlinx.android.synthetic.main.activity_followups.*
import kotlinx.android.synthetic.main.fragment_add_comment.*

class AddCommentsFragment (val listener: AddCommentsFragmentListener) : BaseDialogFragment(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_add_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //loadingDialog = requireContext().getLoadingDialog(0, R.string.fetching_location, theme = R.style.AlertDialogCustom)

        btn_add_comment_ok.setOnClickListener{

            showLoader()
            dismissKeyboard()
            hideLoader()

            val comment = PostComments(tip_add_comment?.editText?.text.toString())

            if (tip_add_comment?.editText?.text.isNullOrEmpty()){

                tip_add_comment.editText?.setError(getString(R.string.comment_field_error))
                tip_add_comment.requestFocus()

            }else{ listener.addCommentFragmentFetch(comment) }
        }

        btn_add_comment_cancel.setOnClickListener {
            dismissKeyboard()
            listener.cancelCommentFragmentFetch()
        }

        view.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if (event?.action == MotionEvent.ACTION_DOWN) {

                    val imm = v?.getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                    v.clearFocus()
                }
                return false
            }
        })
    }

    override fun onClick(v: View?) {
        when (view?.id) {

            R.id.btn_add_comment_ok -> { }

            R.id.btn_add_comment_cancel -> { }
        }
    }

    interface AddCommentsFragmentListener {

        fun addCommentFragmentFetch(comment: PostComments)
        fun cancelCommentFragmentFetch()
    }
}