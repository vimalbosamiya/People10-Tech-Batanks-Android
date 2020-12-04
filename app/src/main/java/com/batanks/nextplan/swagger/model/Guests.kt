package com.batanks.nextplan.swagger.model

data class Guests (

       /* val id : Int,
        val status : String,
        val user : String,
        val user_id : Int,
        val isUser : Boolean*/
        val invitation_id : Int,
        val status : String,
        val name : String,
        val user_id : Int,
        val is_current_user : Boolean,
        val price : String,
        val price_currency : String,
        val email : String,
        val phone_number : String,
        val people_coming : Int

)