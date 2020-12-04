package com.batanks.nextplan.swagger.model

data class Creator (
        val id : Int,
        val username: String,
        val email: String,
        val first_name: String,
        val last_name: String,
        val phone_number: Long?,
        val picture: String)