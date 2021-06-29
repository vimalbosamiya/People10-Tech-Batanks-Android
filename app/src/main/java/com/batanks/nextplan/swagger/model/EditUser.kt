package com.batanks.nextplan.swagger.model

import okhttp3.MultipartBody

data class EditUser (

        val email : String,
        val first_name : String,
        val last_name : String,
        val phone_number : String
        /*val picture : MultipartBody.Part*/
)