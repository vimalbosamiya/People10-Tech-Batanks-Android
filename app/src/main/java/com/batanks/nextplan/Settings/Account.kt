package com.batanks.nextplan.Settings

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
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.User
import kotlinx.android.synthetic.main.activity_account.*

class Account : BaseAppCompatActivity() {

    private val profileViewModel: ProfileModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                ProfileModel(it)
            }
        }).get(ProfileModel::class.java)
    }
    var user_obj : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        rl_edit_account.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Edit_Account :: class.java)
            //intent.putExtra("values" , user_obj as Serializable)
            startActivity(intent)
        })

        img_account_back.setOnClickListener {

            finish()
        }

        Handler().postDelayed(Runnable {
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
                    Toast.makeText(context() , "Something went wrong", Toast.LENGTH_SHORT)
                }
            }
        })
    }
}
