package com.batanks.nextplan.swagger.model

data class Group(
        val id: Int,
        val users: List<Contact>,
        val name: String)