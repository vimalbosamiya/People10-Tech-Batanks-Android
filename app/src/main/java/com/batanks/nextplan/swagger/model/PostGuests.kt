package com.batanks.nextplan.swagger.model

data class PostGuests (

        val users : ArrayList<Int>,
        val phones : ArrayList<Phones>,
        val emails : ArrayList<Emails>
)