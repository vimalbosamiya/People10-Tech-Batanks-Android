package com.batanks.nextplan.swagger.model

import java.io.Serializable

data class User(
        val id: Int,
        val username: String,
        val phone_number: String,
        val contacts: List<Group>,
        val last_login: String,
        val first_name: String,
        val last_name: String,
        val email: String,
        val picture: String,
        val language: String,
        val currency: String,
        val notify_email: Boolean,
        val notify_invitations: Boolean,
        val notify_searches: Boolean,
        val notify_comment: Boolean,
        val token: String) : Serializable