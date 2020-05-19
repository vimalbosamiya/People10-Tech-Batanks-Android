package com.batanks.nextplan.Settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.User

class Edit_Account : AppCompatActivity() {
    //var user_obj : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)

        /*if(intent.hasExtra("values")){
           val user_obj : User? = intent.getSerializableExtra("values") as User?
            System.out.println("");
        }*/
    }
}
