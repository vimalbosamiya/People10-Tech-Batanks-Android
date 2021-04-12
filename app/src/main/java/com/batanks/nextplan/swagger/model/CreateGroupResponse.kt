package com.batanks.nextplan.swagger.model

data class CreateGroupResponse (

        val id : Int,
        val users : ArrayList<GroupUserResponse>,
        val name : String
)