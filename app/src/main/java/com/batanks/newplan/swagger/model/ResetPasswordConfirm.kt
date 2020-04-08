package com.batanks.newplan.swagger.model.mode

data class ResetPasswordConfirm(
        val password1: String,
        val password2: String,
        val email: String,
        val token: String)