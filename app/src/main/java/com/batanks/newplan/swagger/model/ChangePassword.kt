package com.batanks.newplan.swagger.model

data class ChangePassword(
        val password1: String,
        val password2: String,
        val old_password: String)