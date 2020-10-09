package com.batanks.nextplan.swagger.model

data class ResetPassword (
        val password: String,
        val confirmPassword: String,
        val email: String,
        val token: String)