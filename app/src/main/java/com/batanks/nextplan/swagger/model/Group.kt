package com.batanks.nextplan.swagger.model

data class Group(
        val id: Int,
        val users: ArrayList<Contact>,
        val name: String)