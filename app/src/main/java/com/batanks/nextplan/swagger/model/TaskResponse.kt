package com.batanks.nextplan.swagger.model

data class TaskResponse (

        val id : Int,
        val price : String,
        val name : String,
        val description : String,
        val price_currency : String,
        val per_person : Boolean,
        val assignee : Int
)