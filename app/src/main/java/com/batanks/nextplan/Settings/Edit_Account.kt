package com.batanks.nextplan.Settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.User
import kotlinx.android.synthetic.main.activity_edit_account.*

class Edit_Account : AppCompatActivity() {
    //var user_obj : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)

        //input_edit_account_name.setHint(Html.fromHtml(getString(R.string.email_hint)));

        /*if(intent.hasExtra("values")){
           val user_obj : User? = intent.getSerializableExtra("values") as User?
            System.out.println("");
        }*/
    }
}
