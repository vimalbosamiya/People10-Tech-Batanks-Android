package com.batanks.nextplan.swagger.model

data class PostTasks (

        val price : Int,
        val name : String,
        val description : String,
        val per_person : Boolean,
        val assignee : Int?
)