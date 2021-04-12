package com.batanks.nextplan.swagger.model

data class UserSearch (

        val count : Int,
        val next : String,
        val previous : String,
        val results : ArrayList<UserSearchResults>
)