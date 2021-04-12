package com.batanks.nextplan.swagger.model

data class TaskPost(

        val price : String?,
        val name : String?,
        val description : String?,
        val per_person : Boolean?,
        val assignee : Int?
)