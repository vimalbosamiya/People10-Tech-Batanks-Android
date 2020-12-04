package com.batanks.nextplan.swagger.model

import android.net.Uri
import retrofit2.http.Url

data class ContactsList(/*val dummy: Int*/
                        val id : Int,
                        val first_name : String,
                        val last_name : String,
                        val username : String,
                        val email : String,
                        val phone_number : Long,
                        val picture: String,
                        var selection : Boolean)