package com.batanks.nextplan.swagger.model

data class EventDate(
        /*val id: Int,
        val votes : *//*Int*//*ArrayList<Votes>,
        val number : Int,
        val string_value : String,
        val start: String?,
        val end: String?,
        //val votes: MutableList<Int>,
        var visibility : Boolean = false*/
        val id : Int,
        val number : Int,
        val string_value : String,
        val total_votes : Int,
        val current_user_have_vote : Boolean,
        val start : String,
        val end : String,
        var visibility : Boolean = false
)
