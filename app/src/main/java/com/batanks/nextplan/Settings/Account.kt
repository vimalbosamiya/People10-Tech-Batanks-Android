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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        //loadingDialog = this.getLoadingDialog(0, R.string.loading_please_wait, theme = R.style.AlertDialogCustom)

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

        img_account_back.setOnClickListener {

            finish()
        }

        rl_edit_account.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, Edit_Account :: class.java)
            //intent.putExtra("USERDATA" , user_obj as Serializable)
            startActivity(intent)
            finish()
        })
    }
}
