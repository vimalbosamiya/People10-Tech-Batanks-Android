package com.batanks.nextplan.home.fragment.contacts

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import kotlinx.android.synthetic.main.add_contact_fragment.*

class AddContactsFragment : BaseDialogFragment() {

    protected lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    lateinit var add_contacts_contacts_RecyclerView : RecyclerView
    lateinit var add_contacts_groups_RecyclerView : RecyclerView
    lateinit var add_contacts_users_RecyclerView :RecyclerView

    lateinit var select_contacts_phone_checkbox : CheckBox
    /*lateinit var select_contacts_contacts_checkbox : CheckBox
    lateinit var select_contacts_group_checkbox : CheckBox
    lateinit var select_contacts_users_checkbox : CheckBox*/

    lateinit var add_contacts_ok : Button
    lateinit var add_contacts_cancel : Button

    lateinit var adapter : ContactsAdapter
    companion object {
        val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView =  inflater.inflate(R.layout.add_contact_fragment, container, false)

        initView()

        /*select_contacts_phone_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            if(!b){
                recyclerView.visibility = View.GONE
            } else {
                recyclerView.visibility = View.VISIBLE
                select_contacts_contacts_checkbox.isChecked = false
                select_contacts_group_checkbox.isChecked = false
                select_contacts_users_checkbox.isChecked = false
            }
        }
        select_contacts_contacts_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            if(!b){
                add_contacts_contacts_RecyclerView.visibility = View.GONE
            } else {
                add_contacts_contacts_RecyclerView.visibility = View.VISIBLE
                select_contacts_phone_checkbox.isChecked = false
                select_contacts_group_checkbox.isChecked = false
                select_contacts_users_checkbox.isChecked = false
            }
        }
        select_contacts_group_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            if(!b){
                add_contacts_groups_RecyclerView.visibility = View.GONE
            } else {
                add_contacts_groups_RecyclerView.visibility = View.VISIBLE
                select_contacts_phone_checkbox.isChecked = false
                select_contacts_contacts_checkbox.isChecked = false
                select_contacts_users_checkbox.isChecked = false
            }
        }
        select_contacts_users_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            if(!b){
                add_contacts_users_RecyclerView.visibility = View.GONE
            } else {
                add_contacts_users_RecyclerView.visibility = View.VISIBLE
                select_contacts_phone_checkbox.isChecked = false
                select_contacts_contacts_checkbox.isChecked = false
                select_contacts_group_checkbox.isChecked = false
            }
        }*/

        add_contacts_cancel.setOnClickListener(View.OnClickListener {
            dismiss()
        })
        add_contacts_ok.setOnClickListener(View.OnClickListener {
            dismiss()
        })


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        img_contacts_down_arrow_icon.setOnClickListener {

            add_contacts_contacts_RecyclerView.visibility = VISIBLE

            img_contacts_down_arrow_icon.visibility = GONE

            img_contacts_up_arrow_icon.visibility = VISIBLE
        }

        img_contacts_up_arrow_icon.setOnClickListener{

            add_contacts_contacts_RecyclerView.visibility = GONE

            img_contacts_up_arrow_icon.visibility = GONE

            img_contacts_down_arrow_icon.visibility = VISIBLE
        }

        img_group_down_arrow_icon.setOnClickListener {

            add_contacts_groups_RecyclerView.visibility = VISIBLE

            img_group_down_arrow_icon.visibility = GONE

            img_group_up_arrow_icon.visibility = VISIBLE
        }

        img_group_up_arrow_icon.setOnClickListener {

            add_contacts_groups_RecyclerView.visibility = GONE

            img_group_down_arrow_icon.visibility = VISIBLE

            img_group_up_arrow_icon.visibility = GONE
        }

        img_user_down_arrow_icon.setOnClickListener {

            add_contacts_users_RecyclerView.visibility = VISIBLE

            img_user_down_arrow_icon.visibility = GONE

            img_user_up_arrow_icon.visibility = VISIBLE
        }

        img_user_up_arrow_icon.setOnClickListener {

            add_contacts_users_RecyclerView.visibility = GONE

            img_user_up_arrow_icon.visibility = GONE

            img_user_down_arrow_icon.visibility = VISIBLE
        }

        img_ph_contacts_down_arrow_icon.setOnClickListener {

            phone_contacts_RecyclerView.visibility = VISIBLE

            img_ph_contacts_down_arrow_icon.visibility = GONE

            img_ph_contacts_up_arrow_icon.visibility = VISIBLE
        }

        img_ph_contacts_up_arrow_icon.setOnClickListener {

            phone_contacts_RecyclerView.visibility = GONE

            img_ph_contacts_up_arrow_icon.visibility = GONE

            img_ph_contacts_down_arrow_icon.visibility = VISIBLE
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



    private fun initView(){
        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.phone_contacts_RecyclerView)
        add_contacts_contacts_RecyclerView = rootView.findViewById(R.id.add_contacts_contacts_RecyclerView);
        add_contacts_groups_RecyclerView = rootView.findViewById(R.id.add_contacts_groups_RecyclerView);
        add_contacts_users_RecyclerView = rootView.findViewById(R.id.add_contacts_users_RecyclerView);

        add_contacts_contacts_RecyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        add_contacts_groups_RecyclerView.layoutManager = LinearLayoutManager(activity)
        add_contacts_users_RecyclerView.layoutManager = LinearLayoutManager(activity)

        select_contacts_phone_checkbox = rootView.findViewById(R.id.select_contacts_phone_checkbox)
      /*  select_contacts_contacts_checkbox = rootView.findViewById(R.id.select_contacts_contacts_checkbox)
        select_contacts_group_checkbox = rootView.findViewById(R.id.select_contacts_group_checkbox)
        select_contacts_users_checkbox = rootView.findViewById(R.id.select_contacts_users_checkbox)*/
        add_contacts_ok = rootView.findViewById(R.id.add_contacts_ok)
        add_contacts_cancel = rootView.findViewById(R.id.add_contacts_cancel)
        setUpDummyData();
        //recyclerView.adapter = adapter
    }
    private fun setUpDummyData(){
        var list: ArrayList<ContactsModel> = ArrayList<ContactsModel>()
        list.add(ContactsModel("User 1", "12345" , false))
        list.add(ContactsModel("User 2", "12345", false))/*
        list.add(ContactsModel("User 3", "12345", false))
        list.add(ContactsModel("User 4", "12345", false))
        list.add(ContactsModel("User 5", "12345", false))
        list.add(ContactsModel("User 6", "12345", false))
        list.add(ContactsModel("User 7", "12345", false))
        list.add(ContactsModel("User 8", "12345", false))
        list.add(ContactsModel("User 9", "12345", false))*/
        adapter = ContactsAdapter(list)
        recyclerView.adapter = adapter


        if(list.size <=5) {
            val params = add_contacts_contacts_RecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
            params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            add_contacts_contacts_RecyclerView.setLayoutParams(params)
        }
        add_contacts_contacts_RecyclerView.adapter = adapter


        if(list.size <=5) {
            val params1 = recyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
            params1.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            recyclerView.setLayoutParams(params1)
        }
        recyclerView.adapter = adapter


        if(list.size <=5) {
            val params2 = add_contacts_groups_RecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
            params2.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            add_contacts_groups_RecyclerView.setLayoutParams(params2)
        }
        add_contacts_groups_RecyclerView.adapter = adapter


        if(list.size <=5) {
            val params3 = add_contacts_users_RecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
            params3.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            add_contacts_users_RecyclerView.setLayoutParams(params3)
        }
        add_contacts_users_RecyclerView.adapter = adapter


        //loadContacts();
    }

    private fun loadContacts() {
        var builder = StringBuilder()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity?.let {
                    ContextCompat.checkSelfPermission(it,
                            Manifest.permission.READ_CONTACTS)
                } != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                    PERMISSIONS_REQUEST_READ_CONTACTS)
            //callback onRequestPermissionsResult
        } else {
            builder = getContacts()
           // listContacts.text = builder.toString()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            } else {
                //  toast("Permission must be granted in order to display contacts information")
            }
        }
    }

    private fun getContacts(): StringBuilder {
        val builder = StringBuilder()
        val resolver: ContentResolver? = activity?.contentResolver
        val cursor = resolver?.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                null)

        if (cursor != null) {
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor?.getColumnIndex(ContactsContract.Contacts._ID)?.let { cursor.getString(it) }
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneNumber = (cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                    if (phoneNumber > 0) {
                        val cursorPhone = activity?.contentResolver?.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                        if (cursorPhone != null) {
                            if(cursorPhone.count > 0) {
                                while (cursorPhone.moveToNext()) {
                                    val phoneNumValue = cursorPhone.getString(
                                            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                    builder.append("Contact: ").append(name).append(", Phone Number: ").append(
                                            phoneNumValue).append("\n\n")
                                    Log.e("Name ===>",phoneNumValue);
                                }
                            }
                        }
                        if (cursorPhone != null) {
                            cursorPhone.close()
                        }
                    }
                }
            } else {
                //   toast("No contacts available!")
            }
        }
        if (cursor != null) {
            cursor.close()
        }
        return builder
    }
}