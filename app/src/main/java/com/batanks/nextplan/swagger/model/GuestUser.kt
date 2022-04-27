package com.batanks.nextplan.swagger.model

data class GuestUser (

        val id : Int,
        val username : String,
        val email : String,
        val first_name : String,
        val last_name : String,
        val phone_number : String,
        val picture : String,
        var selection : Boolean = false)