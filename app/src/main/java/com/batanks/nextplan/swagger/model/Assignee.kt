package com.batanks.nextplan.swagger.model

data class Assignee (

        val id : Int,
        val first_name : String,
        val last_name : String,
        val username : String?,
        val email : String,
        val phone_number : Long,
        val picture : String?
)