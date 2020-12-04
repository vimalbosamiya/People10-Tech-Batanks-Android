package com.batanks.nextplan.swagger.model

data class Task(
        val id: Int,
        //val price: String,
        val price : Double,
        val name: String,
        val description: String,
        val price_currency: String,
        val per_person: Boolean,
        //val assignee: Int?,
        //val assigneeName :String,
        val assignee : Assignee)