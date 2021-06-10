package com.batanks.nextplan.swagger.model

data class Group(
        val id: Int,
        val users: ArrayList<ContactsList>,
        val name: String,
        var selection : Boolean = false)