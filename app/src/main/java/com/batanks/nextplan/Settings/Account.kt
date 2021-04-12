package com.batanks.nextplan.Settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.viewmodel.ProfileModel
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.home.ModelPreferencesManager
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.User
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_account.*

class Account : BaseAppCompatActivity() {

    private val profileViewModel: ProfileModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                ProfileModel(it)
            }
        }).get(ProfileModel::class.java)
    }

    //var user_obj : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        //loadingDialog = this.getLoadingDialog(0, R.string.loading_please_wait, theme = R.style.AlertDialogCustom)

      /*  profileViewModel.getUserProfile()

        profileViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {

                    val response_User = response.data as User


                    val id: Int? = response_User?.id
                    val userName: String? = response_User?.username
                    val firstName: String? = response_User?.first_name
                    val lastName: String? = response_User?.last_name
                    val email: String? = response_User?.email
                    val phoneNumber: String? = response_User?.phone_number
                    val image : String? = response_User?.picture

                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putInt("ID", id!!).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("USERNAME", userName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("FIRSTNAME", firstName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("LASTNAME", lastName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("EMAIL", email).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("PHONENUMBER", phoneNumber).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("PROFILEIMAGE", image).apply()

                    //getSharedPreferences(RetrofitClient.USER_TOKEN_PREF, Context.MODE_PRIVATE).edit().putString(RetrofitClient.USER_TOKEN_PREF,response_User.token).apply()

                    //RetrofitClient.getRetrofitInstance(this).sharedPref.edit().putString("USER_LOGIN_TOKEN",response_User.token).apply()

                    getSharedPreferences(RetrofitClient.USER_TOKEN_PREF, Context.MODE_PRIVATE).edit().putString("USER_LOGIN_TOKEN",response_User.token).apply()

                    //RetrofitClient.token = response_User.token

                    //println("token from Sign in activity" + RetrofitClient.token)
                    //println(response_User)
                    hideLoader()
                    val intent = Intent(this, HomePlanPreview::class.java)
                    startActivity(intent)
                    finish()
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    //Toast.makeText(this,"Username or Password is incorrect",Toast.LENGTH_LONG).show()
                }
            }
        })*/

        val userId : Int = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getInt("ID",0)
        val userName : String? = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("USERNAME",null)
        val firstName : String? = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("FIRSTNAME",null)
        val lastName : String? = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("LASTNAME",null)
        val email : String? = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("EMAIL",null)
        val phoneNumber : String? = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("PHONENUMBER",null)
        val profileImage : String? = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("PROFILEIMAGE",null)

        txt_accounts_org_name.text = userName
        txt_accounts_org_fname.text = firstName
        txt_accounts_org_mailid.text = email
        txt_accounts_org_contactno.text = phoneNumber
        txt_accounts_org_pseudo.text = lastName

        if (profileImage != null){

            Glide.with(this).load(profileImage).circleCrop().into(img_account_icon)
        }

        /* val userData = ModelPreferencesManager.get<User>("USER_DATA")

        println("This is the user data " + " " + userData)

        txt_accounts_org_name.text = userData?.username
        txt_accounts_org_fname.text = userData?.first_name
        txt_accounts_org_mailid.text = userData?.email
        txt_accounts_org_contactno.text = userData?.phone_number
        txt_accounts_org_pseudo.text = userData?.last_name*/

        img_account_back.setOnClickListener {

            finish()
        }

        /*Handler().postDelayed(Runnable {
                profileViewModel.getUserProfile()
        }, 0)

        loadingDialog = this.getLoadingDialog(0, R.string.loading_please_wait, theme = R.style.AlertDialogCustom)

        profileViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {

                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()
                    user_obj = response.data as User
                    txt_accounts_org_name.text = user_obj?.username
                    txt_accounts_org_fname.text = user_obj?.first_name
                    txt_accounts_org_mailid.text = user_obj?.email
                    txt_accounts_org_contactno.text = user_obj?.phone_number
                    txt_accounts_org_pseudo.text = user_obj?.last_name
                }

                Status.ERROR -> {
                    hideLoader()
                    Toast.makeText(context() , "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })*/

        rl_edit_account.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, Edit_Account :: class.java)
            //intent.putExtra("USERDATA" , user_obj as Serializable)
            startActivity(intent)
            finish()
        })
    }
}
