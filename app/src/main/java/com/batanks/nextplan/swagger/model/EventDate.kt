package com.batanks.nextplan.swagger.model

data class EventDate(
        val id: Int,
        val start: String,
        val end: String,
        val votes: MutableList<Int>)