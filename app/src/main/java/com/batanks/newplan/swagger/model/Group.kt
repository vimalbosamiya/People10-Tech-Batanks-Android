package com.batanks.newplan.swagger.model

data class Group(
        val id: Int,
        val users: List<Contact>,
        val name: String)