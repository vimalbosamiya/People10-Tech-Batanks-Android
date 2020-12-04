package com.batanks.nextplan.swagger.model

data class EventDate(
        val id: Int,
        val votes : ArrayList<String>,
        val start: String,
        val end: String,
        //val votes: MutableList<Int>,
        var visibility : Boolean = false)
