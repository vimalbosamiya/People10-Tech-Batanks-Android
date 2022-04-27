package com.batanks.nextplan.swagger.model

data class PostTasks (

        //val id : Int?,
        val price : String,
        val name : String,
        val description : String,
        val per_person : Boolean,
        val assignee : Int?,
        val assigneeName : String?
)