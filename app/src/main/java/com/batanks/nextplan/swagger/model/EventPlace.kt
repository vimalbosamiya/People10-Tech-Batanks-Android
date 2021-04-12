package com.batanks.nextplan.swagger.model

data class EventPlace(
       /* val id: Int,
        val place: Place,
        val votes: ArrayList<Votes>,
        val number : Int,
        val string_value : String,
        var visibility : Boolean = true*/

        val id : Int,
        val place : Place,
        val total_votes : Int,
        val current_user_have_vote : Boolean,
        val number : Int,
        val string_value : String,
        var visibility : Boolean = true
)