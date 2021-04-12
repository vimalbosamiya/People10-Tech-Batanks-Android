package com.batanks.nextplan.Settings

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.eventdetails.viewmodel.AddContactViewModel
import com.batanks.nextplan.home.viewmodel.ContactsViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.search.AddToGroupActivity
import com.batanks.nextplan.swagger.api.ContactsAPI
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.model.AddContact
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_users_info.*

class UsersInfo : BaseAppCompatActivity() {

    var id : Int? = 0
    var userName : String? = null
    var firstName : String? = null
    var lastName : String? = null
    var email : String? = null
    var picture : String? = null
    var phNo : String? = null
    var isContact : Boolean? = true

    private val addContactViewModel: AddContactViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(GroupsAPI::class.java)?.let {
                AddContactViewModel(it)
            }
        }).get(AddContactViewModel::class.java)
    }

    private val contactsViewModel: ContactsViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(ContactsAPI::class.java)?.let {
                ContactsViewModel(it)
            }
        }).get(ContactsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_info)

        id = intent.getIntExtra("ID",0)
        userName = intent.getStringExtra("NAME")
        firstName = intent.getStringExtra("FIRST_NAME")
        lastName = intent.getStringExtra("LAST_NAME")
        email = intent.getStringExtra("EMAIL")
        picture = intent.getStringExtra("PIC")
        phNo = intent.getStringExtra("PHNO")
        isContact = intent.getBooleanExtra("CONTACT", true)

        txt_accounts_org_name.text = userName
        txt_accounts_org_fname.text = firstName
        txt_accounts_org_pseudo.text = lastName
        txt_accounts_org_mailid.text = email
        txt_accounts_org_contactno.text = phNo.toString()

        if (picture != null){

            Glide.with(this).load(picture).circleCrop().into(img_account_icon)
        }

        addContactViewModel.responseLiveData.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    Toast.makeText(this,getString(R.string.contact_added), Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        contactsViewModel.responseLiveDataDel.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    Toast.makeText(this,getString(R.string.contact_deleted), Toast.LENGTH_SHORT).show()

                    val intent : Intent = Intent(this, Contact:: class.java)
                    startActivity( intent)
                }
                Status.ERROR -> {
                    hideLoader()

                    //contactsViewModel.getContactsList()
                    showMessage(response.error?.message.toString())
                    //println("from contacts delete API Error")
                }
            }
        })

        img_account_back.setOnClickListener {

            finish()
            /*val intent = Intent(this,Contact :: class.java)
            startActivity(intent)*/
        }

        img_account_settings.setOnClickListener {

            if (isContact == true){

                removeContactDialog()

            }else if(isContact == false){

                addToContactDialog()
            }
        }
    }

    private fun addToContactDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_add_user)

        val rl_edit_contact_add = dialog.findViewById(R.id.rl_edit_contact_add) as RelativeLayout
        //val addToGroup = dialog.findViewById(R.id.img_contact_add_to_group_right_icon) as ImageView
        val addToGroup = dialog.findViewById(R.id.rl_edit_contact_add_to_groups) as RelativeLayout


        rl_edit_contact_add.setOnClickListener {

            addContactViewModel.addContact(id?.let { it1 -> AddContact(it1) })

            dialog.dismiss()
        }

        addToGroup.setOnClickListener {

            val intent : Intent = Intent(this, AddToGroupActivity:: class.java)
            intent.putExtra("Id", id)
            startActivity( intent)
            finish()

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun removeContactDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_remove_contact)

        //val edit = dialog.findViewById(R.id.rl_edit_contact_edit) as RelativeLayout
        val delete = dialog.findViewById(R.id.rl_edit_contact_delete) as RelativeLayout
        //val addToGroup = dialog.findViewById(R.id.img_contact_add_to_group_right_icon) as ImageView
        val addToGroup = dialog.findViewById(R.id.rl_edit_contact_add_to_groups) as RelativeLayout
        //val add_to_contacts = dialog.findViewById(R.id.rl_edit_contact_add_to_groups) as RelativeLayout

        delete.setOnClickListener {

            contactsViewModel.deleteContact(id.toString())

            dialog.dismiss()
        }

        addToGroup.setOnClickListener {

            val intent : Intent = Intent(this, AddToGroupActivity:: class.java)
            intent.putExtra("Id", id)
            startActivity( intent)
            finish()

            dialog.dismiss()
        }

        dialog.show()

    }
}
