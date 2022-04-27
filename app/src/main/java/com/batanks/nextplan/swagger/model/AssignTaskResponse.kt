package com.batanks.nextplan.swagger.model

data class AssignTaskResponse (

    val id : Int,
    val price : Double,
    val name : String,
    val description : String,
    val price_currency : String,
    val per_person : Boolean,
    val assignee : Assignee
)