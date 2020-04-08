package com.batanks.newplan.swagger.model

data class RegisterUser(
        val password1: String,
        val password2: String,
        val username: String,
        val email: String,
        val first_name: String,
        val last_name: String,
        val phone_number: String)